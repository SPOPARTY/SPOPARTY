package com.spoparty.api.football.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.football.entity.Cheer;
import com.spoparty.api.football.entity.CheerFixture;
import com.spoparty.api.football.repository.CheerFixtureRepository;
import com.spoparty.api.football.repository.CheerRepository;
import com.spoparty.api.football.response.CheerFixtureDTO;
import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheerServiceImpl implements CheerService {

	private final CheerFixtureRepository cheerFixtureRepository;
	private final CheerRepository cheerRepository;

	@Transactional
	@Override
	// 경기가 끝나면 경기 응원을 내림. -> deleted 처리
	public void deleteEndCheerFixture() {
		List<CheerFixture> cheerFixtures = cheerFixtureRepository.findEndCheerFixture();

		if (cheerFixtures == null)
			return;

		for (CheerFixture cheerFixture : cheerFixtures) {
			cheerFixture.softDelete();
			for (Cheer cheer : cheerFixture.getCheers()) {
				cheer.softDelete();
			}
		}
	}

	@Override
	public ResponseDTO findCheerFixture(PrincipalDetails principalDetails) {

		List<CheerFixture> cheerFixtures = cheerFixtureRepository.findCheerFixture();

		if (!emptyCheckCheerFixture(cheerFixtures)) {
			return ResponseDTO.toDTO(null, "조회된 경기 응원이 없습니다.");
		}

		List<CheerFixtureDTO> cheerFixtureDTOs = EntityToDTOCheerFixture(cheerFixtures);

		// 로그인 중일 경우, 투표한 경기 응원만 결과 볼 수 있게 하기
		if (principalDetails != null) {
			long memberId = principalDetails.getMember().getId();

			List<Cheer> cheers = cheerRepository.findMemberCheer(memberId);

			for (CheerFixtureDTO cheerFixtureDTO : cheerFixtureDTOs) {
				for (Cheer cheer : cheers) {
					if (cheerFixtureDTO.getCheerFixtureId() == cheer.getCheerFixture().getId()) {
						cheerFixtureDTO.switchAlreadyCheer();
						break;
					}
				}
				if (!cheerFixtureDTO.isAlreadyCheer()) {
					cheerFixtureDTO.setCountAsNull();
				}
			}
		}

		return ResponseDTO.toDTO(cheerFixtureDTOs, "응원 진행 경기 조회 성공");
	}

	@Override
	public long makeCheer(int memberId, int cheerFixtureId, int teamId) {
		return cheerRepository.makeCheer(memberId, cheerFixtureId, teamId);

	}

	private boolean emptyCheckCheerFixture(List<CheerFixture> cheerFixtures) {
		if (cheerFixtures.isEmpty()) {
			System.out.println("조회된 경기 응원이 없습니다.");
			return false;
		} else {
			System.out.println("조회된 경기 응원이 있습니다.");
			return true;
		}
	}

	private boolean emptyCheckCheer(long length) {
		if (length == 0) {
			System.out.println("응원 저장이 제대로 되지 않았습니다.");
			return false;
		} else {
			System.out.println("응원이 제대로 저장 되었습니다.");
			return true;
		}
	}

	// Fixture 엔티티를 FixtureDTO로 변환
	private List<CheerFixtureDTO> EntityToDTOCheerFixture(List<CheerFixture> cheerFixtures) {
		List<CheerFixtureDTO> cheerFixtureDTOs = new ArrayList<>();

		for (CheerFixture cheerFixture : cheerFixtures) {
			cheerFixtureDTOs.add(CheerFixtureDTO.toDTO(cheerFixture));
		}

		return cheerFixtureDTOs;
	}

}
