package com.spoparty.api.football.service;

import java.util.List;

import com.spoparty.api.football.response.FixtureEventDTO;
import com.spoparty.api.football.response.ResponseDTO;

public interface FixtureService {
	public ResponseDTO findFixtureByNext(int count);

	// 특정 날짜의 경기 가져오기
	public ResponseDTO findFixtureByDate(String dateStr);

	public ResponseDTO findFixtureByKeyWord(String type, String keyword);

	public ResponseDTO findFixtureByStartEndDate(String startDate, String endDate);

	public ResponseDTO findTeamByKeyword(String keyword);

	public ResponseDTO findLeagueByKeyword(String keyword);

	public List<FixtureEventDTO> findFixtureEvent(int fixtureId);

}
