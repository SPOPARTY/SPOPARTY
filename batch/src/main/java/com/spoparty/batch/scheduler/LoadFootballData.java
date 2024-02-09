package com.spoparty.batch.scheduler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.entity.CheerFixture;
import com.spoparty.batch.entity.Coach;
import com.spoparty.batch.entity.Fixture;
import com.spoparty.batch.entity.FixtureEvent;
import com.spoparty.batch.entity.Lineup;
import com.spoparty.batch.entity.LineupPlayer;
import com.spoparty.batch.entity.Player;
import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.entity.SeasonLeagueTeamPlayer;
import com.spoparty.batch.entity.Team;
import com.spoparty.batch.repository.CheerFixtureRepository;
import com.spoparty.batch.repository.CoachRepository;
import com.spoparty.batch.repository.FixtureEventRepository;
import com.spoparty.batch.repository.FixtureRepository;
import com.spoparty.batch.repository.LeagueRepository;
import com.spoparty.batch.repository.LineupPlayerRepository;
import com.spoparty.batch.repository.LineupRepository;
import com.spoparty.batch.repository.PlayerRepository;
import com.spoparty.batch.repository.SeasonLeagueRepository;
import com.spoparty.batch.repository.SeasonLeagueTeamPlayerRepository;
import com.spoparty.batch.repository.SeasonLeagueTeamRepository;
import com.spoparty.batch.repository.TeamRepository;
import com.spoparty.batch.scheduler.model.CoachResponse;
import com.spoparty.batch.scheduler.model.Coaches;
import com.spoparty.batch.scheduler.model.EventResponse;
import com.spoparty.batch.scheduler.model.Events;
import com.spoparty.batch.scheduler.model.Fixtures;
import com.spoparty.batch.scheduler.model.FixturesResponse;

import com.spoparty.batch.scheduler.model.LineupPlayers;
import com.spoparty.batch.scheduler.model.LineupResponse;
import com.spoparty.batch.scheduler.model.Lineups;

import com.spoparty.batch.scheduler.model.PlayerResponse;
import com.spoparty.batch.scheduler.model.Players;

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
	private final SeasonLeagueRepository seasonLeagueRepository;
	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final SeasonLeagueTeamPlayerRepository seasonLeagueTeamPlayerRepository;
	private final LineupRepository lineupRepository;
	private final LineupPlayerRepository lineupPlayerRepository;
	private final FixtureEventRepository fixtureEventRepository;

	private List<Long> fixtureIdInProgress = new ArrayList<>();


	// 시즌리그정보로 [팀, 코치, 시즌리그구단] 테이블을 만들어줌
	// @Scheduled(fixedRate = 1000*60*60*24)
	public void loadSLT() {

		List<SeasonLeague> sl = seasonLeagueRepository.findAll();
		for (SeasonLeague seasonLeague : sl){

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("season", seasonLeague.getSeason().getId()+"");
			queryParams.add("league", seasonLeague.getLeague().getId()+"");
			ResponseEntity<?> response = footballApiUtil.sendRequest("/teams", queryParams, TeamResponse.class);
			if (response.getStatusCode() == HttpStatus.OK){
				TeamResponse body = (TeamResponse)response.getBody();
				List<TeamInfo> list = body.getResponse();

				for (TeamInfo data : list){
					try {
						Team team = Team.builder()
							.id(data.getTeam().getId())
							.nameKr(data.getTeam().getName())
							.nameEng(data.getTeam().getName())
							.logo(data.getTeam().getLogo())
							.build();
						log.info("team: {}",team);
						teamRepository.save(team);

						queryParams = new LinkedMultiValueMap<>();
						queryParams.add("team", team.getId()+"");
						response = footballApiUtil.sendRequest("/coachs", queryParams, CoachResponse.class);
						CoachResponse body2 = (CoachResponse)response.getBody();
						List<Coaches> list2 = body2.getResponse();
						Coach coach = null;
						for (Coaches cc :list2){
							coach = Coach.builder()
								.id(cc.getId())
								.age(cc.getAge())
								.photo(cc.getPhoto())
								.nameKr(cc.getName())
								.nameEng(cc.getName())
								.nationality(cc.getNationality())
								.build();
						}
						log.info("coach: {}",coach);
						if(coach != null){
							coachRepository.save(coach);
						}
						SeasonLeagueTeam saveData = SeasonLeagueTeam.builder()
							.seasonLeague(seasonLeague)
							.team(team)
							.coach(coach)
							.build();
						log.info("seasonLeagueTeam: {}", saveData);
						seasonLeagueTeamRepository.save(saveData);
					}catch (Exception e){
						log.error(e.toString());
						log.error("fail id : {}",data.getTeam().getId());
					}
				}
			}
		}
	}

	// 시즌리그팀 정보로 [선수, 시즌리그구단선수] 테이블을 만듬
	// @Scheduled(fixedRate = 1000*60*60*24)
	public void loadSLTP() {

		List<SeasonLeagueTeam> lis = seasonLeagueTeamRepository.findAll();
		for(SeasonLeagueTeam slt : lis){
			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("league", slt.getSeasonLeague().getLeague().getId()+"");
			queryParams.add("season", slt.getSeasonLeague().getSeason().getId()+"");
			queryParams.add("team", slt.getTeam().getId()+"");
			ResponseEntity<?> response = footballApiUtil.sendRequest("/players", queryParams, PlayerResponse.class);

			if (response.getStatusCode() == HttpStatus.OK){
				PlayerResponse body = (PlayerResponse)response.getBody();
				List<Players> list = body.getResponse();

				for (Players data : list){
					try {
						Player player = Player.builder()
							.id(data.getPlayer().getId())
							.nameKr(data.getPlayer().getName())
							.nameEng(data.getPlayer().getName())
							.photo(data.getPlayer().getPhoto())
							.age(data.getPlayer().getAge())
							.height(data.getPlayer().getHeight())
							.weight(data.getPlayer().getWeight())
							.nationality(data.getPlayer().getNationality())
							.build();
						log.info("player: {}", player);
						playerRepository.save(player);

						SeasonLeagueTeamPlayer saveData = SeasonLeagueTeamPlayer.builder()
							.seasonLeagueTeam(slt)
							.player(player)
							.build();
						log.info("SeasonLeagueTeamPlayer: {}", saveData);
						seasonLeagueTeamPlayerRepository.save(saveData);
					}catch (Exception e){
						log.error("fail id : {}",data.getPlayer().getId()+e.getMessage());
					}
				}
			}
		}
	}


	// 시즌리그와 시즌리그구단 으로 [경기]테이블 생성
	// @Scheduled(fixedRate = 1000*60*60*24)
	public void loadFixture() {

		List<SeasonLeague> sl = seasonLeagueRepository.findAll();
		for (SeasonLeague seasonLeague : sl){

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("season", seasonLeague.getSeason().getId()+"");
			queryParams.add("league", seasonLeague.getLeague().getId()+"");
			ResponseEntity<?> response = footballApiUtil.sendRequest("/fixtures", queryParams, FixturesResponse.class);
			if (response.getStatusCode() == HttpStatus.OK){
				FixturesResponse body = (FixturesResponse)response.getBody();
				List<Fixtures> list = body.getResponse();

				for (Fixtures data : list){
					try {
						SeasonLeagueTeam home = null;
						SeasonLeagueTeam away = null;
						LocalDateTime ldt = null;
						if(data.getTeams().getHome() != null){
							home = seasonLeagueTeamRepository.findByTeam_Id(data.getTeams().getHome().getId()*1L);
						}
						if(data.getTeams().getAway() != null){
							away = seasonLeagueTeamRepository.findByTeam_Id(data.getTeams().getAway().getId()*1L);
						}
						if (!data.getFixture().getDate().isEmpty()){
							OffsetDateTime odt = OffsetDateTime.parse(data.getFixture().getDate());
							ldt = odt.toLocalDateTime();
						}
						Fixture fixture = Fixture.builder()
							.id(data.getFixture().getId())
							.seasonLeague(seasonLeague)
							.homeTeam(home)
							.awayTeam(away)
							.homeTeamGoal((data.getGoals().getHome()==null)? 0:Integer.parseInt(data.getGoals().getHome()))
							.awayTeamGoal((data.getGoals().getAway()==null)? 0:Integer.parseInt(data.getGoals().getAway()))
							.roundEng(data.getLeague().getRound())
							.roundKr(data.getLeague().getRound())
							.startTime(ldt)
							.status(data.getFixture().getStatus().get("long"))
							.build();

						log.info("fixture: {}", fixture);
						fixtureRepository.save(fixture);

					}catch (Exception e){
						log.error(e.toString());
						log.error("fail id : {}",data.getFixture().getId());
					}
				}
			}
		}
	}


	// 경기와 구단으로 [라인업, 라인업선수] 테이블 생성
	// @Scheduled(fixedRate = 1000*60*60*24)
	public void loadLineup() {

		List<Fixture> fix = fixtureRepository.findAll();
		for (Fixture fff : fix){

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("fixture", fff.getId()+"");
			ResponseEntity<?> response = footballApiUtil.sendRequest("/fixtures/lineups", queryParams, LineupResponse.class);
			if (response.getStatusCode() == HttpStatus.OK){
				LineupResponse body = (LineupResponse)response.getBody();
				List<Lineups> list = body.getResponse();

				for (Lineups data : list){

					try {
						SeasonLeagueTeam team = null;
						if(data.getTeam() != null){
							team = seasonLeagueTeamRepository.findByTeam_Id(data.getTeam().getId()*1L);
						}

						Lineup lineup = Lineup.builder()
							.fixture(fff)
							.seasonLeagueTeam(team)
							.formation(data.getFormation())
							.build();

						log.info("lineup : {}",lineup);
						lineup = lineupRepository.save(lineup);

						for (LineupPlayers linupPlayer : data.getStartXI()){

							LineupPlayer ppp = LineupPlayer.builder()
								.id((long)linupPlayer.getPlayer().getId())
								.lineup(lineup)
								.number(linupPlayer.getPlayer().getNumber() + "")
								.position(linupPlayer.getPlayer().getPos())
								.grid(linupPlayer.getPlayer().getGrid())
								.name(linupPlayer.getPlayer().getName())
								.build();

							log.info("lineupPlayer : {}",ppp);
							lineupPlayerRepository.save(ppp);

						}

						for (LineupPlayers linupPlayer : data.getSubstitutes()){

							LineupPlayer ppp = LineupPlayer.builder()
								.id((long)linupPlayer.getPlayer().getId())
								.lineup(lineup)
								.number(linupPlayer.getPlayer().getNumber() + "")
								.position(linupPlayer.getPlayer().getPos())
								.grid(linupPlayer.getPlayer().getGrid())
								.name(linupPlayer.getPlayer().getName())
								.build();

							log.info("lineupPlayer : {}",ppp);
							lineupPlayerRepository.save(ppp);

						}



					}catch (Exception e){
						log.error(e.toString());
						log.error("fail id : {}",data.getTeam().getId());
					}
				}
			}
		}
	}


	// 경기로 [경기 이벤트] 테이블 생성
	@Scheduled(fixedRate = 1000*15)
	public void loadEvents() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime t1 = LocalDateTime.now(ZoneId.of("Europe/London"));
		LocalDateTime t2 = t1.minusHours(300);
		List<CheerFixture> cheerFixtures = cheerFixtureRepository.findAll();

		for (CheerFixture cheers : cheerFixtures){
			Fixture fixture = cheers.getFixture();
			if(fixture.getStartTime().compareTo(t1) * fixture.getStartTime().compareTo(t2) > 0) continue;

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("fixture", fixture.getId()+"");
			ResponseEntity<?> response = footballApiUtil.sendRequest("/fixtures/events", queryParams, EventResponse.class);
			if (response.getStatusCode() == HttpStatus.OK){
				EventResponse body = (EventResponse)response.getBody();
				List<Events> list = body.getResponse();

				for (Events data : list){

					try {
						SeasonLeagueTeam team = null;
						LineupPlayer p1 = null;
						LineupPlayer p2 = null;
						if(data.getTeam() != null){
							team = seasonLeagueTeamRepository.findByTeam_Id((long)data.getTeam().getId());
						}
						if (data.getPlayer().getId() != null){
							p1 = lineupPlayerRepository.findById(data.getPlayer().getId().longValue()).orElse(null);
						}
						if (data.getAssist().getId() != null){
							p2 = lineupPlayerRepository.findById(data.getAssist().getId().longValue()).orElse(null);
						}

						FixtureEvent fixtureEvent = FixtureEvent.builder()
							.fixture(fixture)
							.seasonLeagueTeam(team)
							.player(p1)
							.assist(p2)
							.time(Long.parseLong(data.getTime().get("elapsed")))
							.type(data.getType())
							.detail(data.getDetail())
							.build();
						fixtureEventRepository.save(fixtureEvent);
						log.info("fixtureEvent: {}", fixtureEvent);
					}catch (Exception e){
						log.error(e.toString());
						log.error("fail id : {}",data.getTeam().getId());
					}
				}
			}
		}
	}

	// 시즌 리그
	// @Scheduled(fixedRate = 1000*60*60*24)
	public void registerCheer() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime current = LocalDateTime.now(ZoneId.of("Europe/London"));
		LocalDateTime yesterday = current.minusDays(2);
		LocalDateTime tomorrow = current.plusDays(2);

		// 응원경기 테이블에서 지난 응원들 삭제처리
		List<CheerFixture> cheerList = cheerFixtureRepository.findAll();
		for (CheerFixture cheerFixture :cheerList){
			LocalDateTime fixtureTime = cheerFixture.getFixture().getStartTime();
			if (fixtureTime.compareTo(yesterday) * fixtureTime.compareTo(tomorrow) >= 0){
				cheerFixture.softDelete();
				cheerFixtureRepository.save(cheerFixture);
				log.info("delete cheerFixture : {}", cheerFixture);
			}
		}

		// 경기목록에서 24시간 전후의 경기를 응원경기 테이블에 추가
		List<Fixture> fixtures = fixtureRepository.findByStartTimeBetween(yesterday, tomorrow);
		for(Fixture fixture : fixtures){
			CheerFixture tmp = cheerFixtureRepository.findByFixture_Id(fixture.getId());
			if (tmp == null){
				CheerFixture saveData = CheerFixture.builder()
					.fixture(fixture)
					.homeCount(0)
					.awayCount(0)
					.build();

				cheerFixtureRepository.save(saveData);
				log.info("save cheerFixture : {}", saveData);
			}
		}
	}

}
