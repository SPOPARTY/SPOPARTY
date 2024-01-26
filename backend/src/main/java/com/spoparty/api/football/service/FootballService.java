package com.spoparty.api.football.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.response.FixtureDTO;
import com.spoparty.api.football.response.ResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FootballService {

	private final FixtureRepository fixtureRepository;



	// 현재 시간부터 시작하는 경기 6개를 가져오기
	public ResponseDTO findFixtureByNext(int count) {
		LocalDateTime now = LocalDateTime.now();

		List<Fixture> fixtures = fixtureRepository.findFixtureByNext(now, count);

		if (emptyCheck(fixtures)) {

			List<FixtureDTO> fixtureDTOs = EntityToDTO(fixtures);

			return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");
		}

		return ResponseDTO.toDTO(null, "예정된 경기 없음");
	}



	// 특정 날짜의 경기 가져오기
	public ResponseDTO findFixtureByDate(String dateStr) {
		// LocalDateTime dateTime = dateTimeFormatter(dateStr);
		List<Fixture> fixtures = fixtureRepository.findFixtureByDate(startDateTimeFormatter(dateStr), endDateTimeFormatter(dateStr));


		if (!emptyCheck(fixtures)) {
			return ResponseDTO.toDTO(null, "해당 날짜에 경기 없음");
		}


		List<FixtureDTO> fixtureDTOs = EntityToDTO(fixtures);

		return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");
	}

	public ResponseDTO findFixtureByKeyWord(String type, String keyword){

		List<Fixture> fixtures = null;

		if (type.equals("리그")) {
			fixtures = fixtureRepository.findFixtureByLeague(keyword);
		} else if (type.equals("팀")) {
			fixtures = fixtureRepository.findFixtureByTeam(keyword);
		} else {
			System.out.println("타입이 잘못 입력되었습니다");
		}

		if (!emptyCheck(fixtures)) {

			return ResponseDTO.toDTO(null, "해당 검색어에 해당하는 경기 없음");

		}


			List<FixtureDTO> fixtureDTOs = EntityToDTO(fixtures);

			return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");



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
	private boolean emptyCheck(List<Fixture> fixtures) {

		if (fixtures.isEmpty()) {
			System.out.println("조회된 경기 일정이 없습니다.");
			return false;
		} else {
			System.out.println("조회된 경기가 있습니다.");
			return true;
		}
	}


	// Fixture 엔티티를 FixtureDTO로 변환
	private List<FixtureDTO> EntityToDTO(List<Fixture> fixtures) {
		List<FixtureDTO> fixtureDTOs = new ArrayList<>();

		for (Fixture f : fixtures) {
			fixtureDTOs.add(FixtureDTO.toDTO(f));
		}

		return fixtureDTOs;
	}
}
