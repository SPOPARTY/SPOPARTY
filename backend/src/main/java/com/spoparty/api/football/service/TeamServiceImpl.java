package com.spoparty.api.football.service;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.entity.SeasonLeagueTeamPlayer;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.response.SeasonLeagueTeamAllInfoDTO;
import com.spoparty.api.member.entity.FollowingTeamProjection;
import com.spoparty.api.member.service.MemberService;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

	private final CommonService common;
	private final SeasonLeagueTeamRepository seasonLeaugeTeamRepository;
	private final MemberService memberService;

	public ResponseDTO getTeamAllInfo(int teamId, @AuthenticationPrincipal
	PrincipalDetails principalDetails) {
		SeasonLeagueTeam seasonLeagueTeam = seasonLeaugeTeamRepository.findTeamAllInfo(teamId);

		if (seasonLeagueTeam == null) {
			return ResponseDTO.toDTO(null, "조회되는 팀 없음");
		}

		SeasonLeagueTeamAllInfoDTO seasonLeagueTeamAllInfoDTO = null;
		if (seasonLeagueTeam.getStandings().size() > 1) {
			log.info("하나이상!");
			List<SeasonLeagueTeamPlayer> tmp =  seasonLeagueTeam.getSeasonLeagueTeamPlayers();
			seasonLeagueTeamAllInfoDTO = SeasonLeagueTeamAllInfoDTO.toDTO(
				seasonLeagueTeam, seasonLeagueTeam.getStandings().get(1));
		} else if (seasonLeagueTeam.getStandings().size() == 1) {
			seasonLeagueTeamAllInfoDTO = SeasonLeagueTeamAllInfoDTO.toDTO(
				seasonLeagueTeam, seasonLeagueTeam.getStandings().get(0));
		} else {
			seasonLeagueTeamAllInfoDTO = SeasonLeagueTeamAllInfoDTO.toDTO(
				seasonLeagueTeam, null);
		}

		// 로그인 중이라면
		if (principalDetails != null) {
			long memberId = principalDetails.getMember().getId();

			// 팔로우중인 팀 표시하기
			for (FollowingTeamProjection followingTeam : memberService.getFollowList((long)memberId)) {
				if (followingTeam.getTeam_id() == seasonLeagueTeamAllInfoDTO.getTeamId()) {
					seasonLeagueTeamAllInfoDTO.switchFollowing();
					break;
				}
			}

		}

		return ResponseDTO.toDTO(seasonLeagueTeamAllInfoDTO, "팀 전체 정보 조회 성공");
	}

	private boolean emptyCheckTeam(SeasonLeagueTeam seasonLeagueTeam) {
		if (seasonLeagueTeam == null) {
			log.info("조회된 팀이 없습니다.");
			return false;
		} else {
			log.info("조회된 팀이 있습니다.");
			return true;
		}
	}
}
