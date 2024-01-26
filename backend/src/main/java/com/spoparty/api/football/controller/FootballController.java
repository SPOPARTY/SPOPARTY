package com.spoparty.api.football.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.football.repository.FixtureRepository;
import com.spoparty.api.football.response.FixtureDTO;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.api.football.service.FootballService;
import com.spoparty.common.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/football")
@RequiredArgsConstructor
public class FootballController {

	private final FootballService footballService;





	// 메인에 띄우는 다가올 가장 이른 경기 6개
	@GetMapping(value = "/fixtures", params = {"next"})
	ResponseEntity<ResponseDTO>findFixtureByNext(@RequestParam(value = "next") int next){

		ResponseDTO responseDTO = footballService.findFixtureByNext(next);


		// HttpStatusCode code = isContentExist(responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}


	// 날짜별로 경기 일정 조회
	@GetMapping(value = "/fixtures",  params = {"date"})
	ResponseEntity<ResponseDTO> findFixtureByDate(@RequestParam(value = "date") String date) {

		ResponseDTO responseDTO = footballService.findFixtureByDate(date);

		// HttpStatusCode code = isContentExist(responseDTO);
		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}


	// 리그/팀으로 경기 일정 조회
	@GetMapping(value = "/fixtures", params = {"type", "keyword"})
	ResponseEntity<ResponseDTO>findFixtureByKeyword(@RequestParam(value = "type") String type, @RequestParam(value = "keyword") String keyword) throws
		UnsupportedEncodingException {


		ResponseDTO responseDTO = footballService.findFixtureByKeyWord(type, keyword);

		// HttpStatusCode code = isContentExist(responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
	}


	// 파티에서 시청가능한 경기 조회
	@GetMapping(value = "/fixtures", params = {"startDate", "endDate"})
	ResponseEntity<ResponseDTO> findFixtureByStartEndDate(@RequestParam(value = "startDate") String startDate,@RequestParam(value = "endDate") String endDate){


		ResponseDTO responseDTO = footballService.findFixtureByStartEndDate(startDate, endDate);

		// HttpStatusCode code = isContentExist(responseDTO);

		return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));

	}




	// private HttpStatusCode isContentExist(ResponseDTO responseDTO) {
	// 	if (responseDTO.getData() == null) {
	// 		return HttpStatusCode.valueOf(200);
	// 	} else {
	// 		return HttpStatusCode.valueOf(200);
	// 	}
	// }




}