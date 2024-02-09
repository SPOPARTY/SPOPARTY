package com.spoparty.api.football.response.comparator;

import java.util.Comparator;

import com.spoparty.api.football.response.SeasonLeagueTeamStandingDTO;
import com.spoparty.api.football.response.StandingDTO;

public class SeasonLeagueTeamStandingDTOCompare implements Comparator<SeasonLeagueTeamStandingDTO> {
	@Override
	public int compare(SeasonLeagueTeamStandingDTO o1, SeasonLeagueTeamStandingDTO o2) {
		StandingDTO o1Standing = o1.getStanding();
		StandingDTO o2Standing = o2.getStanding();
		if (o1Standing.getGroup().compareTo(o2Standing.getGroup()) < 0){
			return -1;
		} else if (o1Standing.getGroup().compareTo(o2Standing.getGroup()) > 0) {
			return 1;
		} else if (o1Standing.getGroup().compareTo(o2Standing.getGroup()) == 0){
			if (o1Standing.getRank() < o2Standing.getRank()) {
				return -1;
			}else if (o1Standing.getRank() > o2Standing.getRank()){
				return 1;
			}
		}
		return 0;
	}
}
