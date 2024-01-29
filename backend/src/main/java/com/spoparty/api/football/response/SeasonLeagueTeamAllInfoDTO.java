package com.spoparty.api.football.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spoparty.api.football.entity.Coach;
import com.spoparty.api.football.entity.Lineup;
import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.entity.SeasonLeagueTeamPlayer;
import com.spoparty.api.football.entity.Standings;
import com.spoparty.api.football.entity.Team;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class SeasonLeagueTeamAllInfoDTO {

	private long seasonLeagueTeamId;

	private long teamId;

	private String nameKr;

	private String nameEng;

	private String logo;

	private boolean following = false;

	private StandingDTO standing;

	private CoachDTO coach;

	private List<PlayerDTO> players;


	public void switchFollowing() {
		following = true;
	}


	public static SeasonLeagueTeamAllInfoDTO toDTO(SeasonLeagueTeam entity) {

		Team team = entity.getTeam();
		StandingDTO standingDTO = StandingDTO.toDTO(entity.getStanding());
		CoachDTO coachDTO = CoachDTO.toDTO(entity.getCoach());

		List<PlayerDTO> players = new ArrayList<>();

		for(SeasonLeagueTeamPlayer player : entity.getSeasonLeagueTeamPlayers()){
			players.add(PlayerDTO.toDTO(player));
		};

		return SeasonLeagueTeamAllInfoDTO.builder()
			.seasonLeagueTeamId(entity.getId())
			.teamId(team.getId())
			.nameKr(team.getNameKr())
			.nameEng(team.getNameEng())
			.logo(team.getLogo())
			.players(players)
			.standing(standingDTO)
			.coach(coachDTO)
			.build();
	}

}
