package com.spoparty.api.football.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.entity.SeasonLeague;
import com.spoparty.api.football.entity.SeasonLeagueTeam;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.repository.SeasonLeagueRepository;
import com.spoparty.api.football.repository.SeasonLeagueTeamRepository;
import com.spoparty.api.football.response.FixtureDTO;
import com.spoparty.api.football.response.KeywordSeasonLeagueDTO;
import com.spoparty.api.football.response.KeywordSeasonLeagueTeamDTO;
import com.spoparty.api.football.response.ResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FixtureService {

	private final FixtureRepository fixtureRepository;

	private final SeasonLeagueTeamRepository seasonLeagueteamRepository;

	private final SeasonLeagueRepository seasonLeagueRespository;

	private final CommonService commonService;

	// 현재 시간부터 시작하는 경기 6개를 가져오기
	public ResponseDTO findFixtureByNext(int count) {
		LocalDateTime now = LocalDateTime.now();
		List<Fixture> fixtures = fixtureRepository.findFixtureByNext(now, count);

		if (!emptyCheckFixture(fixtures)) {
			return ResponseDTO.toDTO(null, "예정된 경기 없음");
		}

		List<FixtureDTO> fixtureDTOs = EntityToDTOFixture(fixtures);

		return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");
	}

	// 특정 날짜의 경기 가져오기
	public ResponseDTO findFixtureByDate(String dateStr) {
		// LocalDateTime dateTime = dateTimeFormatter(dateStr);
		List<Fixture> fixtures = fixtureRepository.findFixtureByDate(startDateTimeFormatter(dateStr),
			endDateTimeFormatter(dateStr));

		if (!emptyCheckFixture(fixtures)) {
			return ResponseDTO.toDTO(null, "해당 날짜에 경기 없음");
		}

		List<FixtureDTO> fixtureDTOs = EntityToDTOFixture(fixtures);

		return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");
	}

	public ResponseDTO findFixtureByKeyWord(String type, String keyword) {

		List<Fixture> fixtures = null;

		if (type.equals("리그")) {
			fixtures = fixtureRepository.findFixtureByLeague(keyword);
		} else if (type.equals("팀")) {
			fixtures = fixtureRepository.findFixtureByTeam(keyword);
		} else {
			System.out.println("타입이 잘못 입력되었습니다");
		}

		if (!emptyCheckFixture(fixtures)) {

			return ResponseDTO.toDTO(null, "해당 검색어에 해당하는 경기 없음");

		}

		List<FixtureDTO> fixtureDTOs = EntityToDTOFixture(fixtures);

		return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");
	}

	public ResponseDTO findFixtureByStartEndDate(String startDate, String endDate) {

		LocalDateTime start = startDateTimeFormatter(startDate);
		LocalDateTime end = endDateTimeFormatter(endDate);

		List<Fixture> fixtures = fixtureRepository.findFixtureByDate(start, end);

		if (!emptyCheckFixture(fixtures)) {
			return ResponseDTO.toDTO(null, "해당 기간에 경기 없음");
		}

		List<FixtureDTO> fixtureDTOs = EntityToDTOFixture(fixtures);

		return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");
	}

	public ResponseDTO findTeamByKeyword(String keyword) {
		List<SeasonLeagueTeam> seasonLeagueTeams = seasonLeagueteamRepository.findTeamByKeyword(keyword);

		if (!commonService.emptyCheckTeam(seasonLeagueTeams)) {
			return ResponseDTO.toDTO(null, "해당 팀 없음");
		}

		List<KeywordSeasonLeagueTeamDTO> keywordSeasonLeagueTeamDTOs = EntityToDTOTeam(seasonLeagueTeams);

		return ResponseDTO.toDTO(keywordSeasonLeagueTeamDTOs, "팀 조회 성공");
	}

	public ResponseDTO findLeagueByKeyword(String keyword) {

		List<SeasonLeague> seasonLeagues = seasonLeagueRespository.findLeagueByKeyword(keyword);

		if (!commonService.emptyCheckLeague(seasonLeagues)) {
			return ResponseDTO.toDTO(null, "해당 리그 없음");
		}

		List<KeywordSeasonLeagueDTO> keywordSeasonLeagueDTOs = EntityToDTOLeague(seasonLeagues);

		return ResponseDTO.toDTO(keywordSeasonLeagueDTOs, "리그 조회 성공");
	}

	// 특정 날짜 조회시, between으로 조회하기 위해
	// 오늘 하루의 시작 시간 얻기
	private LocalDateTime startDateTimeFormatter(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(date + " 00:00:00", formatter);
	}

	// 특정 날짜 조회시, between으로 조회하기 위해
	// 오늘 하루 끝나는 시간 얻기
	private LocalDateTime endDateTimeFormatter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(date + " 23:59:59", formatter);
	}

	// 레포지토리에서 조회한 데이터가 있는지 체크
	private boolean emptyCheckFixture(List<Fixture> fixtures) {
		if (fixtures.isEmpty()) {
			System.out.println("조회된 경기 일정이 없습니다.");
			return false;
		} else {
			System.out.println("조회된 경기가 있습니다.");
			return true;
		}
	}

	// Fixture 엔티티를 FixtureDTO로 변환
	private List<FixtureDTO> EntityToDTOFixture(List<Fixture> fixtures) {
		List<FixtureDTO> fixtureDTOs = new ArrayList<>();

		for (Fixture f : fixtures) {
			fixtureDTOs.add(FixtureDTO.toDTO(f));
		}

		return fixtureDTOs;
	}

	private List<KeywordSeasonLeagueDTO> EntityToDTOLeague(List<SeasonLeague> seasonLeagues) {
		List<KeywordSeasonLeagueDTO> KeywordSeaonLeagueDTOs = new ArrayList<>();

		for (SeasonLeague l : seasonLeagues) {
			KeywordSeaonLeagueDTOs.add(KeywordSeasonLeagueDTO.toDTO(l));
		}

		return KeywordSeaonLeagueDTOs;
	}

	private List<KeywordSeasonLeagueTeamDTO> EntityToDTOTeam(List<SeasonLeagueTeam> seasonLeagueTeams) {
		List<KeywordSeasonLeagueTeamDTO> KeywordSeaonLeagueTeamDTOs = new ArrayList<>();

		for (SeasonLeagueTeam t : seasonLeagueTeams) {
			KeywordSeaonLeagueTeamDTOs.add(KeywordSeasonLeagueTeamDTO.toDTO(t));
		}

		return KeywordSeaonLeagueTeamDTOs;
	}
}
