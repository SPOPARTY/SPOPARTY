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




	private LocalDateTime startDateTimeFormatter(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(date + " 00:00:00", formatter);
	}

	private LocalDateTime endDateTimeFormatter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(date + " 23:59:59", formatter);
	}

	private LocalDate dateFormatter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}





	public ResponseDTO<List<FixtureDTO>> findFixtureByDate(String dateStr) {
		// LocalDateTime dateTime = dateTimeFormatter(dateStr);
		List<Fixture> fixtures = fixtureRepository.findFixtureByDate(startDateTimeFormatter(dateStr), endDateTimeFormatter(dateStr));

		if (fixtures.isEmpty())
			System.out.println("조회된 경기 일정이 없습니다.");
		else {
			System.out.println("조회된 경기가 있습니다.");
		}

		List<FixtureDTO> fixtureDTOs = new ArrayList<>();

		for(Fixture f : fixtures) {
			fixtureDTOs.add(FixtureDTO.toDTO(f));
		}

		return ResponseDTO.toDTO(fixtureDTOs, "경기 조회 성공");
	}
}
