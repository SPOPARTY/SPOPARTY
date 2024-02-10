package com.spoparty.batch.scheduler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.spoparty.batch.entity.CheerFixture;
import com.spoparty.batch.entity.Fixture;
import com.spoparty.batch.entity.FixtureEvent;
import com.spoparty.batch.entity.LineupPlayer;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.repository.CheerFixtureRepository;
import com.spoparty.batch.repository.FixtureEventRepository;
import com.spoparty.batch.repository.FixtureRepository;
import com.spoparty.batch.repository.LineupPlayerRepository;
import com.spoparty.batch.repository.LineupRepository;
import com.spoparty.batch.repository.SeasonLeagueRepository;
import com.spoparty.batch.repository.SeasonLeagueTeamRepository;
import com.spoparty.batch.scheduler.model.EventResponse;
import com.spoparty.batch.scheduler.model.Events;
import com.spoparty.batch.scheduler.model.Fixtures;
import com.spoparty.batch.scheduler.model.FixturesResponse;
import com.spoparty.batch.util.FootballApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

	private final FootballApiUtil footballApiUtil;
	private final CheerFixtureRepository cheerFixtureRepository;
	private final FixtureRepository fixtureRepository;
	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;
	private final LineupPlayerRepository lineupPlayerRepository;
	private final FixtureEventRepository fixtureEventRepository;


	// 응원 테이블 관리
	@Scheduled(fixedRate = 1000*60*60)
	public void registerCheer() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime current = LocalDateTime.now(ZoneId.of("Europe/London"));
		LocalDateTime yesterday = current.minusDays(1);
		LocalDateTime tomorrow = current.plusDays(1);

		// 응원경기 테이블에서 지난 응원들 삭제처리
		List<CheerFixture> cheerList = cheerFixtureRepository.findAll();
		for (CheerFixture cheerFixture :cheerList){
			LocalDateTime fixtureTime = cheerFixture.getFixture().getStartTime();
			if (fixtureTime.compareTo(yesterday) * fixtureTime.compareTo(tomorrow) >= 0){
				cheerFixture.softDelete();
				cheerFixtureRepository.save(cheerFixture);
				fixtureEventRepository.deleteByFixture_Id(cheerFixture.getFixture().getId());
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

	// 경기로 [경기 이벤트] 테이블 생성
	@Scheduled(fixedRate = 1000*60)
	public void loadEvents() {

		// 응원 경기 테이블에 있는 경기 중, 현재시간과 3시간 이전 사이의 경기들의 events를 조회하여 테이블에 추가
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime t1 = LocalDateTime.now(ZoneId.of("Europe/London"));
		LocalDateTime t2 = t1.minusHours(3);
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

				outer : for (Events data : list){

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
						List<FixtureEvent> events = fixtureEventRepository.findByFixture_IdAndTime(fixture.getId(), Long.parseLong(data.getTime().get("elapsed")));
						for (FixtureEvent ddddd : events){
							if ( ddddd.equals(fixtureEvent)){
								continue outer;
							}
						}
						fixtureEventRepository.save(fixtureEvent);
						log.info("fixtureEvent: {}", fixtureEvent);
					}catch (Exception e){
						log.error(e.toString());
						log.error("fail id : {}",data.getTeam().getId());
					}
				}
			}

			// 경기 테이블을 경기 현황에 맞추어 갱신시켜 주기 위해 경기중인 경기의 경기테이블의 정보도 갱신
			queryParams = new LinkedMultiValueMap<>();
			queryParams.add("id", cheers.getFixture().getId()+"");
			response = footballApiUtil.sendRequest("/fixtures", queryParams, FixturesResponse.class);

			if (response.getStatusCode() == HttpStatus.OK){
				FixturesResponse body = (FixturesResponse)response.getBody();
				List<Fixtures> list = body.getResponse();

				for (Fixtures data : list){
					Fixture ffff = fixtureRepository.findById(data.getFixture().getId()*1L).orElse(null);
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
						Fixture fix = Fixture.builder()
							.id(data.getFixture().getId())
							.seasonLeague(ffff.getSeasonLeague())
							.homeTeam(home)
							.awayTeam(away)
							.homeTeamGoal((data.getGoals().getHome()==null)? 0:Integer.parseInt(data.getGoals().getHome()))
							.awayTeamGoal((data.getGoals().getAway()==null)? 0:Integer.parseInt(data.getGoals().getAway()))
							.roundEng(data.getLeague().getRound())
							.roundKr(data.getLeague().getRound())
							.startTime(ldt)
							.status(data.getFixture().getStatus().get("long"))
							.build();

						log.info("fixture: {}", fix.getId());
						fixtureRepository.save(fix);

					}catch (Exception e){
						log.error(e.toString());
						log.error("fail id : {}",data.getFixture().getId());
					}
				}
			}

		}
	}

}
