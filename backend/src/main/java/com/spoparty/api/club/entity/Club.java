package com.spoparty.api.club.entity;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import com.spoparty.api.common.entity.BaseEntity;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.party.entity.Party;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "clubMembers")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = 0")
public class Club extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id", unique = true, nullable = false)
	private Long id;

	@Setter
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	// @ColumnDefault("6")
	private Integer maxParticipants = 6;

	@Column(nullable = false)
	// @ColumnDefault("0")
	private Integer currentParticipants = 0;

	@OneToMany(mappedBy = "club")
	private List<ClubMember> clubMembers = new ArrayList<>(); // 그룹원

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member hostMember;

	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "party_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Party party;


	// 생성 메서드
	public static Club createClub(String name, Member member) {
		Club club = new Club();
		club.name = name;
		club.hostMember = member;
		return club;
	}

	// 비즈니스 로직
	public void addClubMember(ClubMember clubMember) {
		if (currentParticipants.equals(maxParticipants)) {
			throw new IllegalStateException(ENOUGH_GROUP_PARTICIPANTS.getMessage());
		}
		this.currentParticipants++;
		clubMembers.add(clubMember);
	}

	public void removeClubMember(ClubMember clubMember) {
		if (currentParticipants <= 0) {
			throw new IllegalStateException(NOT_ENOUGH_GROUP_PARTICIPANTS.getMessage());
		}
		this.currentParticipants--;
		clubMembers.remove(clubMember);
	}

	public void deleteClub() {
		for (ClubMember clubMember : clubMembers) {
			clubMember.softDelete();
		}
		this.softDelete();
		clubMembers.clear();
		currentParticipants = 0;
	}
}
