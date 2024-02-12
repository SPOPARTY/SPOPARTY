// package com.spoparty.api.vote.service;
//
// import static org.assertj.core.api.AssertionsForClassTypes.*;
//
// import java.util.List;
//
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
//
// import com.spoparty.api.vote.domain.Vote;
// import com.spoparty.api.vote.dto.VoteCreateRequestDTO;
// import com.spoparty.api.vote.repository.OptionRepository;
// import com.spoparty.api.vote.repository.PenaltyRepository;
// import com.spoparty.api.vote.repository.VoteRepository;
//
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// class VoteServiceTest {
// 	@Autowired
// 	VoteService voteService;
//
// 	@Autowired
// 	VoteRepository voteRepository;
//
// 	@Autowired
// 	OptionRepository optionRepository;
//
// 	@Autowired
// 	PenaltyRepository penaltyRepository;
//
// 	@Test
// 	void create() {
// 		VoteCreateRequestDTO dto = VoteCreateRequestDTO.builder()
// 			.memberId("1")
// 			.nickname("월클")
// 			.title("10분 뒤 이기고 있을 팀?")
// 			.options(List.of("전북현대", "FC서울"))
// 			.penalty("만원빵")
// 			.build();
//
// 		Vote response = voteService.create("333", dto);
// 		System.out.println(response);
// 	}
//
// 	@Test
// 	void getVote() {
// 		String id = "3876a51d-65db-48ac-a4c5-98b113c69096";
// 		Vote response = voteService.getVote(id);
// 		assertThat(response.getVoteId()).isEqualTo(id);
// 		System.out.println(response);
// 	}
//
// 	@Test
// 	void getAllInfos() {
// 		System.out.println(voteRepository.findAll());
// 		System.out.println(optionRepository.findAll());
// 		System.out.println(penaltyRepository.findAll());
// 	}
//
// 	@Test
// 	void remove() {
// 		voteRepository.deleteAll();
// 		optionRepository.deleteAll();
// 		penaltyRepository.deleteAll();
// 	}
// }