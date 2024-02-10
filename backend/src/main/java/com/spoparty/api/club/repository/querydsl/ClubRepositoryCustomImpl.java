package com.spoparty.api.club.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoparty.api.club.dto.response.ClubResponseDTO;
import com.spoparty.api.club.dto.response.QClubResponseDTO;
import com.spoparty.api.club.entity.QClub;
import com.spoparty.api.club.entity.QClubMember;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryCustomImpl implements ClubRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	QClubMember qClubMember = QClubMember.clubMember;
	QClubMember qClubMemberSub = new QClubMember("clubMemberSub");
	QClub qClub = QClub.club;

	@Override
	public List<ClubResponseDTO> findClubsAndClubMemberCountOrderByTime(Long memberId) {
		return jpaQueryFactory
			.select(new QClubResponseDTO(qClub, qClubMember.count().intValue()))
			.from(qClub)
			.join(qClubMember).on(qClubMember.club.id.eq(qClub.id))
			.where(qClubMember.isDeleted.isFalse())
			.join(qClubMemberSub).on(qClubMemberSub.club.id.eq(qClub.id))
			.where(qClubMemberSub.member.id.eq(memberId))
			.where(qClubMemberSub.isDeleted.isFalse())
			.groupBy(qClub.id)
			.orderBy(qClub.updatedTime.desc())
			.fetch();
	}
}