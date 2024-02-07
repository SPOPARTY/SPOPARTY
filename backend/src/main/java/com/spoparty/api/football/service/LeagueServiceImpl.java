package com.spoparty.api.football.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.entity.Standings;
import com.spoparty.api.football.repository.SeasonLeagueRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.response.SeasonLeagueDTO;
import com.spoparty.api.football.response.SeasonLeagueTeamStandingDTO;
import com.spoparty.api.member.entity.FollowingTeamProjection;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeagueServiceImpl implements LeagueService {

	private final SeasonLeagueRepository seasonLeagueRepository;

	private final SeasonLeagueTeamRepository seasonLeagueTeamRepository;

	private final CommonService commonService;

	private final MemberService memberService;

	public ResponseDTO findAllLeague() {
		List<SeasonLeague> seasonLeagues = seasonLeagueRepository.findAllLeague();

		if (!commonService.emptyCheckLeague(seasonLeagues)) {
			return ResponseDTO.toDTO(null, "리그 정보 없음");
		}

		List<SeasonLeagueDTO> seasonLeagueDTOs = entityToDTOLeague(seasonLeagues);

		return ResponseDTO.toDTO(seasonLeagueDTOs, "리그 정보 조회 성공");
	}

	public ResponseDTO findTeamRank(int leagueId, PrincipalDetails principalDetails) {
		List<SeasonLeagueTeam> seasonLeagueTeams = seasonLeagueTeamRepository.findTeamRank(leagueId);

		if (!commonService.emptyCheckTeam(seasonLeagueTeams)) {
			return ResponseDTO.toDTO(null, "구단 순위 정보 없음");
		}

		List<SeasonLeagueTeamStandingDTO> seasonLeagueTeamStandingDTOs = entityToDTOTeamStanding(seasonLeagueTeams);

		// 로그인 중이라면
		if (principalDetails != null) {
			long memberId = principalDetails.getMember().getId();

			// 팔로우중인 팀 표시하기
			for (FollowingTeamProjection followingTeam : memberService.getFollowList((long)memberId)) {
				for (SeasonLeagueTeamStandingDTO teamDTO : seasonLeagueTeamStandingDTOs) {

					if (followingTeam.getTeam_id() == teamDTO.getTeamId()) {
						teamDTO.switchFollowing();
						break;
					}
				}
			}

		}
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
			List<Standings> standings = t.getStandings();
			for(int i = 0; i<standings.size(); i++) {
				seasonLeagueTeamStandingDTOs.add(SeasonLeagueTeamStandingDTO.toDTO(t, standings.get(i)));
			}

		}

		return seasonLeagueTeamStandingDTOs;
	}

}
