package com.spoparty.api.club.entity;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.spoparty.api.common.entity.BaseEntity;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id")
	private long id;

	@Column(nullable = false)
	private String name;

	@ColumnDefault("6")
	private int maxParticipants;

	@ColumnDefault("1")
	private int currentParticipants;

	@OneToMany(mappedBy = "club")
	private List<ClubMember> clubMembers = new ArrayList<>(); // 그룹원

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	// private Member hostMember;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "party_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Party party;

	// 연관관계 매핑
	private void addClubMember(ClubMember clubMember) {
		clubMembers.add(clubMember);
		clubMember.setClub(this);
	}

	public void setParty(Party party) {
		this.party = party;
		party.setClub(this);
	}

	// 생성 메서드
	public static Club createClub(String name, ClubMember clubMember) {
		Club club = new Club();
		club.setName(name);
		club.addClubMember(clubMember);
		return club;
	}

	// 비즈니스 로직
	public boolean increaseCurrentParticipants() {
		if (currentParticipants == maxParticipants)
			throw new IllegalStateException(NOT_ENOUGH_GROUP_PARTICIPANTS.getMessage());
		this.currentParticipants++;
		return true;
	}

	public boolean decreaseCurrentParticipants() {
		if (currentParticipants == 0)
			throw new IllegalStateException(ENOUGH_GROUP_PARTICIPANTS.getMessage());
		this.currentParticipants--;
		return true;
	}

	public boolean deleteClubMember(ClubMember clubMember) {
		if (clubMembers.remove(clubMember)) {
			return true;
		}
		throw new IllegalStateException(NO_GROUP_MEMBER.getMessage());
	}

	public boolean joinClubMember(ClubMember clubMember) {
		if (clubMembers.contains(clubMember)) {
			throw new IllegalStateException(ALREADY_GROUP_MEMBER.getMessage());
		}
		this.addClubMember(clubMember);
		return true;
	}
}
