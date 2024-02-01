package com.spoparty.api.party.entity;

import static com.spoparty.api.common.constants.ErrorCode.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.common.entity.BaseEntity;
import com.spoparty.api.common.exception.BadRequestException;
import com.spoparty.api.football.entity.Fixture;
import com.spoparty.api.member.entity.Member;

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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "club")
@Where(clause = "is_deleted = 0")
public class Party extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "party_id")
	private Long id;

	@Column(nullable = false) // openVidu sessionId
	private String openviduSessionId;

	@Setter
	@Column(length = 30)
	private String title;

	@Column(nullable = false)
	@ColumnDefault("6")
	private Integer maxParticipants = 6;

	@Column(nullable = false)
	@ColumnDefault("0")
	@Min(0) @Max(6)
	private Integer currentParticipants = 0;

	@Setter
	// @Max(2048)
	private String fixtureUrl;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fixture_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member host;

	public static Party createParty(Member host, Club club, String openviduSessionId) {
		Party party = new Party();
		party.host = host;
		party.openviduSessionId = openviduSessionId;
		club.setParty(party);
		return party;
	}

	// 비즈니스 로직
	public void increaseParticipants() {
		if (maxParticipants.equals(currentParticipants)) {
			throw new BadRequestException(CANNOT_CREATE_PARTY_MEMBER);
		}
		currentParticipants++;
	}

	public void decreaseParticipants() {
		if (currentParticipants == 0) {
			throw new BadRequestException(CANNOT_CREATE_PARTY_MEMBER);
		}
		currentParticipants--;
	}

	// public void deleteParty() {
	// 	club.setParty(null); // null로 초기화
	// 	softDelete();
	// }
}
