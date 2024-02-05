package com.spoparty.api.party.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import com.spoparty.api.club.entity.Club;
import com.spoparty.api.common.entity.BaseEntity;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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
	private Integer maxParticipants = 6;

	@Setter
	private String fixtureUrl;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fixture_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member hostMember;

	public static Party createParty(Member hostMember, Club club, String openviduSessionId) {
		Party party = new Party();
		party.hostMember = hostMember;
		party.openviduSessionId = openviduSessionId;
		club.setParty(party);
		return party;
	}
}
