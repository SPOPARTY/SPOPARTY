package com.spoparty.api.football.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonService {

	protected boolean emptyCheckLeague(List<SeasonLeague> seasonLeagues) {

		if (seasonLeagues.isEmpty()) {
			log.info("조회된 리그가 없습니다.");
			return false;
		} else {
			log.info("조회된 리그가 있습니다.");
			return true;
		}
	}

	protected boolean emptyCheckTeam(List<SeasonLeagueTeam> seasonLeagueTeams) {

		if (seasonLeagueTeams.isEmpty()) {
			log.info("조회된 팀이 없습니다.");
			return false;
		} else {
			log.info("조회된 팀이 있습니다.");
			return true;
		}
	}

}
