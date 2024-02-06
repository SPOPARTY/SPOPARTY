package com.spoparty.batch.scheduler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.entity.CheerFixture;
import com.spoparty.batch.entity.Coach;
import com.spoparty.batch.entity.Fixture;
import com.spoparty.batch.entity.League;
import com.spoparty.batch.entity.Player;
import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.Team;
import com.spoparty.batch.repository.CheerFixtureRepository;
import com.spoparty.batch.repository.CoachRepository;
import com.spoparty.batch.repository.FixtureRepository;
import com.spoparty.batch.repository.LeagueRepository;
import com.spoparty.batch.repository.PlayerRepository;
import com.spoparty.batch.repository.TeamRepository;
import com.spoparty.batch.scheduler.model.CoachResponse;
import com.spoparty.batch.scheduler.model.Coaches;
import com.spoparty.batch.scheduler.model.Fixtures;
import com.spoparty.batch.scheduler.model.FixturesResponse;
import com.spoparty.batch.scheduler.model.LeagueResponse;
import com.spoparty.batch.scheduler.model.Leagues;
import com.spoparty.batch.scheduler.model.PlayerResponse;
import com.spoparty.batch.scheduler.model.Players;
import com.spoparty.batch.scheduler.model.Season;
import com.spoparty.batch.scheduler.model.TeamInfo;
import com.spoparty.batch.scheduler.model.TeamResponse;
import com.spoparty.batch.util.FootballApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadFootballData {

	private final FootballApiUtil footballApiUtil;
	private final CheerFixtureRepository cheerFixtureRepository;
	private final FixtureRepository fixtureRepository;
	private final PlayerRepository playerRepository;
	private final LeagueRepository leagueRepository;
	private final TeamRepository teamRepository;
	private final CoachRepository coachRepository;

	private List<Long> fixtureIdInProgress = new ArrayList<>();
	private int[] leagues = {1, 7, 8, 18, 15, 532, 965, 1012, 17, 292, 293, 295, 294, 48, 143, 556, 78, 529, 81, 66, 526, 119};

	// @Scheduled(fixedRate = 1000*60*60*24)
	public void loadPlayers() {

		for (int leagueId : leagues){
			for (int i = 2000; i <2024 ; i++) {
				MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
				queryParams.add("league", leagueId+"");
				queryParams.add("season", i+"");
				ResponseEntity<?> response = footballApiUtil.sendRequest("/players", queryParams, PlayerResponse.class);
				if (response.getStatusCode() == HttpStatus.OK){
					PlayerResponse body = (PlayerResponse)response.getBody();
					List<Players> list = body.getResponse();

					for (Players data : list){
						try {
						Player saveData = Player.builder()
							.id(data.getPlayer().getId())
							.nameKr(data.getPlayer().getFirstname()+" "+data.getPlayer().getLastname())
							.nameEng(data.getPlayer().getFirstname()+" "+data.getPlayer().getLastname())
							.age(data.getPlayer().getAge())
							.nationality((data.getPlayer().getNationality()==null)? "":data.getPlayer().getNationality())
							.height((data.getPlayer().getHeight() == null)? "":data.getPlayer().getHeight().split(" ")[0])
							.weight((data.getPlayer().getWeight() == null)? "":data.getPlayer().getWeight().split(" ")[0])
							.photo(data.getPlayer().getPhoto())
							.position(data.getStatistics().get(0).getGames().getPosition())
							.captain(data.getStatistics().get(0).getGames().isCaptain()).build();
						log.info(saveData.toString());
						playerRepository.save(saveData);
						}catch (Exception e){
							log.error("fail id : {}",data.getPlayer().getId());
						}
					}
				}


			}
		}

	}

	// public void loadLeagues() {
	//
	// 		for (int i = 2000; i <2024 ; i++) {
	// 			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
	// 			queryParams.add("season", i+"");
	// 			ResponseEntity<?> response = footballApiUtil.sendRequest("/leagues", queryParams, LeagueResponse.class);
	// 			if (response.getStatusCode() == HttpStatus.OK){
	// 				LeagueResponse body = (LeagueResponse)response.getBody();
	// 				List<Leagues> list = body.getResponse();
	//
	// 				for (Leagues data : list){
	// 					try {
	// 					League saveData = League.builder()
	// 						.id(data.getLeague().getId())
	// 						.nameKr((data.getLeague().getName()==null)? "":data.getLeague().getName())
	// 						.nameEng((data.getLeague().getName()==null)? "":data.getLeague().getName())
	// 						.logo((data.getLeague().getLogo()==null)? "":data.getLeague().getLogo())
	// 						.country((data.getLeague().getCountry().getName()==null)? "":data.getLeague().getCountry().getName())
	// 						.countryLogo((data.getLeague().getCountry().getFlag()==null)? "":data.getLeague().getCountry().getFlag())
	// 						.type((data.getLeague().getType()==null)? "":data.getLeague().getType())
	// 						.build();
	// 					log.info(saveData.toString());
	// 					leagueRepository.save(saveData);
	// 					}catch (Exception e){
	// 						log.error("fail id : {}",data.getLeague().getId());
	// 					}
	//
	// 					for (Season s : data.getSeasons()){
	// 						SeasonLeague sl = SeasonLeague.builder()
	// 							.season()
	// 							.league()
	// 							.seasonStartDate()
	// 							.seasonEndDate()
	// 							.build();
	// 					}
	//
	//
	// 					try {
	// 						Player saveData = Player.builder()
	//
	// 						log.info(saveData.toString());
	// 						playerRepository.save(saveData);
	// 					}catch (Exception e){
	// 						log.error("fail id : {}",data.getPlayer().getId());
	// 					}
	// 				}
	// 			}
	//
	//
	// 		}
	//
	// }


	// @Scheduled(fixedRate = 1000*60*60*24)
	public void loadTeams() {

		for (int leagueId : leagues){
			for (int i = 2000; i <2024 ; i++) {
				MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
				queryParams.add("league", leagueId+"");
				queryParams.add("season", i+"");
				ResponseEntity<?> response = footballApiUtil.sendRequest("/teams", queryParams, TeamResponse.class);
				if (response.getStatusCode() == HttpStatus.OK){
					TeamResponse body = (TeamResponse)response.getBody();
					List<TeamInfo> list = body.getResponse();

					for (TeamInfo data : list){
						try {
							Team saveData = Team.builder()
								.id(data.getTeam().getId())
								.nameKr((data.getTeam().getName()==null)? "":data.getTeam().getName())
								.nameEng((data.getTeam().getName()==null)? "":data.getTeam().getName())
								.logo((data.getTeam().getLogo()==null)? "":data.getTeam().getLogo())
								.build();
							log.info(saveData.toString());
							teamRepository.save(saveData);
						}catch (Exception e){
							log.error(e.toString());
							log.error("fail id : {}",data.getTeam().getId());
						}
					}
				}


			}
		}

	}

	@Scheduled(fixedRate = 1000*60*60*24)
	public void loadCoatch() {


		List<Team> list = teamRepository.findAll();
		for (Team team  : list){
			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("team", team.getId()+"");
			ResponseEntity<?> response = footballApiUtil.sendRequest("/coachs", queryParams, CoachResponse.class);
			if (response.getStatusCode() == HttpStatus.OK){
				CoachResponse body = (CoachResponse)response.getBody();
				List<Coaches> list2 = body.getResponse();

				for (Coaches data : list2){
					try {
						Coach saveData = Coach.builder()
							.id(data.getId())
							.nameKr((data.getName()==null)? "":data.getName())
							.nameEng((data.getName()==null)? "":data.getName())
							.age(data.getAge())
							.nationality((data.getNationality()==null)? "":data.getNationality())
							.photo((data.getPhoto()==null)? "":data.getPhoto())
							.build();
						coachRepository.save(saveData);
					}catch (Exception e){
						log.error(e.toString());
						log.error("fail id : {}",data.getTeam().getId());
					}
				}
			}
		}

	}




}
