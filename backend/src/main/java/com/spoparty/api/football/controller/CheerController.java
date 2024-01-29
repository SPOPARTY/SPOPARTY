package com.spoparty.api.football.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.football.repository.CheerRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/football/cheers")
public class CheerController {

	private final CheerRepository cheerRepository;

	@GetMapping
	ResponseEntity findCheerFixture(@AuthenticationPrincipal AuthenticationPrincipal principal) {
		// cheerRepository.
		return null;
	}
}
