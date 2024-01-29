package com.spoparty.api.football.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.repository.SeasonLeagueRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.response.SeasonLeagueDTO;
import com.spoparty.api.football.response.SeasonLeagueTeamStandingDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeagueService {

	private final SeasonLeagueRepository seasonLeagueRepository;

	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;

	private final CommonService commonService;

	public ResponseDTO findAllLeague() {
		List<SeasonLeague> seasonLeagues = seasonLeagueRepository.findAllLeague();

		if (!commonService.emptyCheckLeague(seasonLeagues)) {
			return ResponseDTO.toDTO(null, "리그 정보 없음");
		}

		List<SeasonLeagueDTO> seasonLeagueDTOs = entityToDTOLeague(seasonLeagues);

		return ResponseDTO.toDTO(seasonLeagueDTOs, "리그 정보 조회 성공");
	}

	public ResponseDTO findTeamRank(int leagueId) {
		List<SeasonLeagueTeam> seasonLeagueTeams = seasonLeagueTeamRepository.findTeamRank(leagueId);

		if (!commonService.emptyCheckTeam(seasonLeagueTeams)) {
			return ResponseDTO.toDTO(null, "구단 순위 정보 없음");
		}

		List<SeasonLeagueTeamStandingDTO> seasonLeagueTeamStandingDTOs = entityToDTOTeamStanding(seasonLeagueTeams);

		return ResponseDTO.toDTO(seasonLeagueTeamStandingDTOs, "구단 순위 조회 성공");
	}

	private List<SeasonLeagueDTO> entityToDTOLeague(List<SeasonLeague> seasonLeagues) {
		List<SeasonLeagueDTO> seaonLeagueDTOs = new ArrayList<>();

		for (SeasonLeague l : seasonLeagues) {
			seaonLeagueDTOs.add(SeasonLeagueDTO.toDTO(l));
		}

		return seaonLeagueDTOs;
	}

	private List<SeasonLeagueTeamStandingDTO> entityToDTOTeamStanding(List<SeasonLeagueTeam> seasonLeagueTeams) {
		List<SeasonLeagueTeamStandingDTO> seasonLeagueTeamStandingDTOs = new ArrayList<>();

		for (SeasonLeagueTeam t : seasonLeagueTeams) {
			seasonLeagueTeamStandingDTOs.add(SeasonLeagueTeamStandingDTO.toDTO(t));
		}

		return seasonLeagueTeamStandingDTOs;
	}

}
