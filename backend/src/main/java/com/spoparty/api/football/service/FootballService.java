package com.spoparty.api.football.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.FixtureRepository;

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





	public List<Fixture> findFixtureByDate(String dateStr) {
		// LocalDateTime dateTime = dateTimeFormatter(dateStr);

		return fixtureRepository.findByStartTimeBetween(startDateTimeFormatter(dateStr), endDateTimeFormatter(dateStr));
	}
}
