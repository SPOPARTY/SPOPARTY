package com.spoparty.api.football.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.response.FixtureDTO;
import com.spoparty.api.football.response.KeywordSeasonLeagueDTO;
import com.spoparty.api.football.response.KeywordSeasonLeagueTeamDTO;
import com.spoparty.api.football.response.ResponseDTO;

public interface FixtureService {
	public ResponseDTO findFixtureByNext(int count);

	// 특정 날짜의 경기 가져오기
	public ResponseDTO findFixtureByDate(String dateStr) ;
	public ResponseDTO findFixtureByKeyWord(String type, String keyword);

	public ResponseDTO findFixtureByStartEndDate(String startDate, String endDate);

	public ResponseDTO findTeamByKeyword(String keyword) ;

	public ResponseDTO findLeagueByKeyword(String keyword);


}
