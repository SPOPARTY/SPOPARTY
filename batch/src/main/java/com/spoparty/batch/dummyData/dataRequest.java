package com.spoparty.batch.dummyData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.dto.SeasonId;
import com.spoparty.batch.dummyData.model.OuterStanding;
import com.spoparty.batch.dummyData.model.Response;
import com.spoparty.batch.dummyData.model.Standing;
import com.spoparty.batch.dummyData.model.StandingLeague;
import com.spoparty.batch.dummyData.model.StandingResponse;
import com.spoparty.batch.dummyData.model.StandingScore;
import com.spoparty.batch.entity.League;
import com.spoparty.batch.entity.LeagueType;
import com.spoparty.batch.entity.Season;
import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.entity.Standings;
import com.spoparty.batch.entity.Team;
import com.spoparty.batch.repository.LeagueRepository;
import com.spoparty.batch.repository.SeasonLeagueRepository;
import com.spoparty.batch.repository.SeasonLeagueTeamRepository;
import com.spoparty.batch.repository.SeasonRepository;
import com.spoparty.batch.repository.StandingRepository;
import com.spoparty.batch.repository.TeamRepository;
import com.spoparty.batch.util.FootballApiUtil;
import com.spoparty.batch.util.LanguageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class dataRequest {

	public final FootballApiUtil apiRequest;
	public final LanguageUtil languageUtil;
	// public final SeasonRepository seasonRepository;
	public final LeagueRepository leagueRepository;
	public final SeasonLeagueRepository seasonLeagueRepository;
	public final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	public final TeamRepository teamRepository;
	public final StandingRepository standingRepository;
	// @Transactional(value = defaultTxManager())

	// @Scheduled(fixedRate=200000)
	public void getAllSeason() {
		// ResponseEntity<Map> response = apiRequest.sendRequest("/leagues/seasons", null, Map.class);
		// List<Integer> seasons = (List<Integer>)response.getBody().get("response");
		//
		//
		// for (Integer season : seasons) {
		// 	Season seasonEntity = Season.builder()
		// 		.value(Integer.toString(season))
		// 		.build();
		//
		// seasonRepository.save(seasonEntity);
		// }
		//
		// System.out.println("end");
	}


	// @Scheduled(fixedRate=300000)
	public void getAllLeague() {
		// System.out.println(LocalTime.MIN);
		// List<Long> leagueIds = new ArrayList<>(Arrays.asList((long)1, (long)7, (long)8, (long)18,
		// 	(long)15, (long)532, (long)965, (long)1012, (long)17, (long)292, (long)293, (long)295, (long)294,
		// 	(long)48, (long)143, (long)556, (long)78, (long)529, (long)81, (long)66, (long)526, (long)119));
		//
		//
		// for (Long id : leagueIds) {
		// 	MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>() {
		// 	};
		//
		// 	queryParams.add("id", Long.toString(id));
		//
		// 	ResponseEntity<Map> response = apiRequest.sendRequest("/leagues", queryParams, Map.class);
		// 	List<Map<String, Object>> data = (List)response.getBody().get("response");
		//
		//
		// 	Map<String, String> league = (Map)data.get(0).get("league");
		// 	Map<String, String> country = (Map)data.get(0).get("country");
		// 	List<Map<String, Object>> seasons = (List)data.get(0).get("seasons");
		//
		//
		//
		//
		//
		// 	String nameKr = languageUtil.translate(league.get("name"));
		// 	String countryNameKr = languageUtil.translate(country.get("name"));
		// 	System.out.println("name " + nameKr);
		// 	System.out.println("country "  + countryNameKr);
		//
		// 	League leagueEntity = League.builder()
		// 		.id(id)
		// 		.nameKr(nameKr)
		// 		.nameEng(league.get("name"))
		// 		.logo(league.get("logo"))
		// 		.country(country.get("name"))
		// 		.countryLogo(country.get("flag"))
		// 		.type(league.get("type"))
		// 		.build();
		//
		// 	leagueRepository.save(leagueEntity);
		//
		// 	for (Map<String, Object> season : seasons){
		//
		//
		// 		Season seasonEntity = seasonRepository.findByValue(String.valueOf(season.get("year")), Season.class);
		//
		// 		SeasonLeague seasonLeague = SeasonLeague.builder()
		// 			.league(leagueEntity)
		// 			.season(seasonEntity)
		// 			.seasonStartDate(ToLocalDateTime((String)season.get("start")))
		// 			.seasonEndDate(ToLocalDateTime((String)season.get("end")))
		// 			.build();
		//
		// 		seasonLeagueRepository.save(seasonLeague);
		// 	}
		//
		// }
		//
		// System.out.println("end");
	}

	// @Scheduled(fixedRate=3000000)
	public void getStandings() {


		String[] leagueIds = new String[]{"140", "78", "292"};

		for (String leagueId : leagueIds) {

			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("season", "2023");
			params.add("league", leagueId);


			ResponseEntity<Response> response = apiRequest.sendRequest("/standings", params, Response.class);

			if (response.getStatusCode() != HttpStatus.OK) {
				log.info("제대로 된 응답이 오지 않음");
				return;
			}

			Response standingResponses= (Response)response.getBody();

			StandingLeague standingLeague = standingResponses.getResponse().get(0).getLeague();
			List<List<Standing>> standingList = standingLeague.getStandings();
			log.info("standingList size " + standingList.size());

			for (int i = 0; i < standingList.size(); i++) {
				List<Standing> standings = standingList.get(i);

				for (Standing standing: standings){


					Team team = teamRepository.findById(standing.getTeam().getId()).orElse(null);
					SeasonLeagueTeam seasonLeagueTeam = seasonLeagueTeamRepository.findByTeam(team);

					StandingScore score = standing.getAll();
					Standings entity = Standings.builder()
						.rank(standing.getRank())
						.points(standing.getPoints())
						.goalDiff(standing.getGoalsDiff())
						.group(standing.getGroup())
						.form(standing.getForm())
						.played(score.getPlayed())
						.win(score.getWin())
						.draw(score.getDraw())
						.lose(score.getLose())
						.goalsFor(score.getGoals().get("for"))
						.goalsAgainst(score.getGoals().get("against"))
						.seasonLeagueTeam(seasonLeagueTeam)
						.build();


					standingRepository.save(entity);
					log.info(String.valueOf(entity.getRank()));
				}
			}


			log.info("저장완료");
		}


	}

	// @Scheduled(fixedRate=3000000)
	public void test() {
		// System.out.println("시작");
		// leagueRepository.findAll();
		// System.out.println("끝");


		TimeZone tz = Calendar.getInstance().getTimeZone();



		System.out.println("현재 TimeZone 구역 의 이름 ::: " + tz.getDisplayName());
	}


	private LocalDateTime ToLocalDateTime(String date) {
		String[] dateUnit = date.split("-");

		LocalDate localDate = LocalDate.of(Integer.parseInt(dateUnit[0]),
			Integer.parseInt(dateUnit[1]), Integer.parseInt(dateUnit[2]));

		return LocalDateTime.of(localDate, LocalTime.MIN);
	}





}
