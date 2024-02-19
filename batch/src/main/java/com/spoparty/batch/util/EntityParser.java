package com.spoparty.batch.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.spoparty.batch.entity.*;
import com.spoparty.batch.entity.Team;
import com.spoparty.batch.repository.SeasonLeagueTeamPlayerRepository;
import com.spoparty.batch.scheduler.model.*;
import com.spoparty.batch.scheduler.model.League;
import com.spoparty.batch.scheduler.model.Player;
import com.spoparty.batch.scheduler.model.Season;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.spoparty.batch.Exception.ApiWrongDataResponseException;

@Component
@RequiredArgsConstructor
public class EntityParser {

	SeasonLeagueTeamPlayerRepository seasonLeagueTeamPlayerRepository;


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
		SeasonLeagueTeam afterSeasonLeagueTeam = null;

		// 팀 정보가 바뀌지 않은 경우
		if (!changeTeamInfo(beforeTeam, afterTeam)) {
			changeTeam = false;
		// 팀 정보가 바뀐 경우
		} else {
			changeTeam = true;
		}

		// 현재 코치가 없는 경우
		if (afterCoach == null) {
			// SeasonLeagueTeam 엔티티에서 coach만 null로 변경한다.
			// afterSeasonLeagueTeam = SeasonLeagueTeam.builder()
			// 		.seasonLeague(item.getSeasonLeague())
			// 		.team(afterTeam)
			// 		.coach(null)
			// 		.build();
			// item.changeCoach(null);
		// 현재 코치가 기존 코치와 동일인물인 경우
		} else if (item.getCoach().getId() == afterCoach.getId()){
			// 코치의 세부 정보가 달라졌다면
			if (changeCoachInfo(item.getCoach(), afterCoach)){
				// // 엔티티에서 코치 정보 수정.
				// afterSeasonLeagueTeam = SeasonLeagueTeam.builder()
				// 	.seasonLeague(item.getSeasonLeague())
				// 	.team(afterTeam)
				// 	.coach(afterCoach)
				// 	.build();
				//
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

		afterSeasonLeagueTeam = SeasonLeagueTeam.builder()
			.seasonLeague(item.getSeasonLeague())
			.team(afterTeam)
			.coach(afterCoach)
			.build();


		afterSeasonLeagueTeam.setId(item.getId());
		return afterSeasonLeagueTeam;


		// return item;
	}

	public List<SeasonLeagueTeamPlayer> seasonLeagueTeamPlayerParser(SeasonLeagueTeam item, List<Player> players) {
		List<SeasonLeagueTeamPlayer> beforePlayers = item.getSeasonLeagueTeamPlayers();
		List<SeasonLeagueTeamPlayer> changePlayers = new ArrayList<>();


		for (Player player : players) {
			boolean isCatch = false;
			for (SeasonLeagueTeamPlayer beforePlayer : beforePlayers) {
				if (beforePlayer.getPlayer().getId() != player.getId()) continue;

				isCatch = true;
				beforePlayers.remove(beforePlayer);
				// 기존의 선수의 정보가 바꼈으면 추가.
				if (changePlayerInfo(beforePlayer.getPlayer(), player)) {

					changePlayers.add(makeSeasonLeagueTeamPlayer(item, player));

				}

					break;

			}

			if (isCatch) continue;

			// 기존 선수중에 선수가 없었다면
			changePlayers.add(makeSeasonLeagueTeamPlayer(item, player));


		}

		for (SeasonLeagueTeamPlayer removePlayer : beforePlayers) {
			seasonLeagueTeamPlayerRepository.delete(removePlayer);
		}

		return changePlayers;
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



	private boolean changePlayerInfo(com.spoparty.batch.entity.Player beforePlayer, Player afterPlayer) {
		if (beforePlayer.getAge() != afterPlayer.getAge()
			|| !beforePlayer.getNameKr().equals(afterPlayer.getName())
			|| !beforePlayer.getNameEng().equals(afterPlayer.getName())
			|| !beforePlayer.getNationality().equals(afterPlayer.getNationality())
			|| !beforePlayer.getHeight().equals(afterPlayer.getHeight())
			|| !beforePlayer.getWeight().equals(afterPlayer.getWeight())
			|| 	!beforePlayer.getPhoto().equals(afterPlayer.getPhoto()))
			return true;
		else
			return false;
	}


	private SeasonLeagueTeamPlayer makeSeasonLeagueTeamPlayer(SeasonLeagueTeam item, Player player) {
		com.spoparty.batch.entity.Player newPlayer = com.spoparty.batch.entity.Player.builder()
				.age(player.getAge())
				.height(player.getHeight())
				.photo(player.getPhoto())
				.weight(player.getWeight())
				.id(player.getId())
				.nameEng(player.getName())
				.nameKr(player.getName())
				.nationality(player.getNationality())
				.build();

		return SeasonLeagueTeamPlayer.builder()
				.seasonLeagueTeam(item)
				.player(newPlayer)
				.build();

	}

}
