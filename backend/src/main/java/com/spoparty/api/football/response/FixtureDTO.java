package com.spoparty.api.football.response;


import java.time.format.DateTimeFormatter;
import com.spoparty.api.football.entity.Fixture;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class FixtureDTO {

	private long fixtureId;

	private String startTime;

	private String round;

	private String status;

	private int homeTeamGoal;


	private int awayTeamGoal;



	private FixtureLeagueDTO league;


	private FixtureTeamDTO homeTeam;


	private FixtureTeamDTO awayTeam;

	public static FixtureDTO toDTO(Fixture entity) {

		FixtureLeagueDTO league = FixtureLeagueDTO.toDTO(entity.getSeasonLeague());
		FixtureTeamDTO homeTeam = FixtureTeamDTO.toDTO(entity.getHomeTeam());
		FixtureTeamDTO awayTeam = FixtureTeamDTO.toDTO(entity.getAwayTeam());

		return FixtureDTO.builder()
			.fixtureId(entity.getId())
			.startTime(entity.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
			.round(entity.getRoundKr())
			.status(entity.getStatus())
			.homeTeamGoal(entity.getHomeTeamGoal())
			.homeTeamGoal(entity.getHomeTeamGoal())
			.awayTeamGoal(entity.getAwayTeamGoal())
			.league(league)
			.homeTeam(homeTeam)
			.awayTeam(awayTeam)
			.build();
	}
}


