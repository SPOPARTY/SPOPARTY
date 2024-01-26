package com.spoparty.api.club.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spoparty.api.club.dto.ClubRequestDto;
import com.spoparty.api.club.repository.ClubRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClubServiceTest {
	@PersistenceContext
	EntityManager em;

	@Autowired
	ClubService clubService;

	@Autowired
	ClubRepository clubRepository;

	@Test
	void createClub() throws Exception{
		ClubRequestDto clubRequestDto = new ClubRequestDto(1, "드르와드르와");
		long clubId = clubService.createClub(clubRequestDto);
	}

	// @Test
	// void findRecentClubs() {
	// }
	//
	// @Test
	// void updateClubName() {
	// }
	//
	// @Test
	// void deleteClub() {
	// }
	//
	// @Test
	// void getInviteUrl() {
	// }
	//
	// @Test
	// void getGroupMembers() {
	// }
	//
	// @Test
	// void createGroupMember() {
	// }
	//
	// @Test
	// void assignHost() {
	// }
	//
	// @Test
	// void deleteGroupMember() {
	// }
}