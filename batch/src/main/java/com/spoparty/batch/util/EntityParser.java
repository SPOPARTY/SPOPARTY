package com.spoparty.batch.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.spoparty.batch.Exception.ApiWrongDataResponseException;
import com.spoparty.batch.entity.Coach;
import com.spoparty.batch.entity.SeasonLeague;
import com.spoparty.batch.entity.SeasonLeagueTeam;
import com.spoparty.batch.entity.Team;
import com.spoparty.batch.scheduler.model.Career;
import com.spoparty.batch.scheduler.model.CoachResponse;
import com.spoparty.batch.scheduler.model.Coaches;
import com.spoparty.batch.scheduler.model.Country;
import com.spoparty.batch.scheduler.model.League;
import com.spoparty.batch.scheduler.model.LeagueResponse;
import com.spoparty.batch.scheduler.model.Leagues;
import com.spoparty.batch.scheduler.model.Season;
import com.spoparty.batch.scheduler.model.TeamResponse;

@Component
public class EntityParser {
	public SeasonLeague seasonLeagueParser(Long seasonLeagueId, LeagueResponse leagueResponse) {
		Leagues leagues = leagueResponse.getResponse().get(0);
		League league = leagues.getLeague();
		Country country = leagues.getCountry();

		Season season = null;
		for (Season s: leagues.getSeasons()){
			if (s.getYear() == 2023) {
				season = s;
				break;
			}
		}

		com.spoparty.batch.entity.League leagueEntity = com.spoparty.batch.entity.League.builder()
			.id(league.getId())
			.nameKr(league.getName())
			.nameEng(league.getName())
			.logo(league.getLogo())
			.country(country.getName())
			.countryLogo(country.getFlag())
			.type(league.getType())
			.build();

		com.spoparty.batch.entity.Season seasonEntity = com.spoparty.batch.entity.Season.builder()
			.id(2023)
			.value("2023")
			.build();


		return SeasonLeague.builder()
			.id(seasonLeagueId)
			.league(leagueEntity)
			.season(seasonEntity)
			.seasonStartDate(ToLocalDateTime((String)season.getStart()))
			.seasonEndDate(ToLocalDateTime((String)season.getEnd()))
			.build();

	}

	public SeasonLeagueTeam seasonLeagueTeamParser(SeasonLeagueTeam item, TeamResponse teamResponse, CoachResponse coachResponse) {
		if (teamResponse == null || coachResponse == null)
			return null;

		Team beforeTeam = item.getTeam();
		com.spoparty.batch.scheduler.model.Team teamInfo = teamResponse.getResponse().get(0).getTeam();
		if (beforeTeam.getId() != teamInfo.getId())
			throw new ApiWrongDataResponseException("잘못된 팀 정보를 가져왔습니다.");

		Team afterTeam = Team.builder()
			.id(teamInfo.getId())
			.nameKr(teamInfo.getName())
			.nameEng(teamInfo.getName())
			.logo(teamInfo.getLogo())
			.build();

		List<Coaches> coaches = coachResponse.getResponse();

		Coach afterCoach = null;
		coachLoop:
			for (Coaches coach : coaches) {
				for (Career career : coach.getCareer()) {
					// if (career.getTeam().getId() != beforeTeam.getId())
					// 	continue;
					if (career.getEnd() == null || isCurrentCoach(career.getStart(), career.getEnd())) {
						afterCoach = Coach.builder()
							.id(coach.getId())
							.photo(coach.getPhoto())
							.nationality(coach.getNationality())
							.age(coach.getAge())
							.nameKr(coach.getName())
							.nameEng(coach.getName())
							.build();
					}
				}
			}
		Boolean changeTeam;

		// 팀 정보가 바뀌지 않은 경우
		if (!changeTeamInfo(beforeTeam, afterTeam)) {
			changeTeam = false;
		// 팀 정보가 바뀐 경우
		} else {
			changeTeam = true;
			item.changeTeam(afterTeam);
		}

		// 현재 코치가 없는 경우
		if (afterCoach == null) {
			// SeasonLeagueTeam 엔티티에서 coach만 null로 변경한다.
			item.changeCoach(null);
		// 현재 코치가 기존 코치와 동일인물인 경우
		} else if (item.getCoach().getId() == afterCoach.getId()){
			// 코치의 세부 정보가 달라졌다면
			if (changeCoachInfo(item.getCoach(), afterCoach)){
				// 엔티티에서 코치 정보 수정.
				item.changeCoach(afterCoach);
			// 코치의 세부정보가 그대로라면
			} else {
				// 팀도 변경사항이 없다면 전부 그대로이므로 SeasonLeagueTeam 수정하지 않는다.
				if (!changeTeam)
					return null;
			}
			// 현재 코치가 기존 코치와 다른 인물인 경우
		} else {
			// 엔티티에서 코치 정보 수정.
			item.changeCoach(afterCoach);
		}


		return item;
	}




	private LocalDateTime ToLocalDateTime(String date) {
		String[] dateUnit = date.split("-");

		LocalDate localDate = LocalDate.of(Integer.parseInt(dateUnit[0]),
			Integer.parseInt(dateUnit[1]), Integer.parseInt(dateUnit[2]));

		return LocalDateTime.of(localDate, LocalTime.MIN);
	}


	private boolean isCurrentCoach(String start, String end) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate current = LocalDate.now(ZoneId.of("Europe/London"));
		LocalDate startTime = LocalDate.parse(start, formatter);
		LocalDate endTime = LocalDate.parse(end, formatter);

		if (current.compareTo(startTime) * current.compareTo(endTime) <= 0 ) return true;
		else return false;
	}


	private boolean changeTeamInfo(Team beforeTeam, Team afterTeam) {
		if (!beforeTeam.getLogo().equals(afterTeam.getLogo()) || !beforeTeam.getNameEng().equals(afterTeam.getNameEng()) || beforeTeam.getNameKr().equals(afterTeam.getNameKr()))
			return true;
		else
			return false;
	}

	private boolean changeCoachInfo(Coach beforeCoach, Coach afterCoach) {
		if (beforeCoach.getAge() != afterCoach.getAge()
			|| !beforeCoach.getNameKr().equals(afterCoach.getNameKr())
			|| !beforeCoach.getNameEng().equals(afterCoach.getNameEng())
			|| !beforeCoach.getNationality().equals(afterCoach.getNationality())
			|| !beforeCoach.getPhoto().equals(afterCoach.getPhoto()))
			return true;
		else
			return false;
	}
}
