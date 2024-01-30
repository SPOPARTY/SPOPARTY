package com.spoparty.api.party.entity;

import org.hibernate.annotations.ColumnDefault;
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
import jakarta.persistence.OneToOne;
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

	@Column(nullable = false, name = "session_id") // openVidu sessionId
	private String sessionId;

	@Column(nullable = false, length = 30)
	private String title;

	@Column(nullable = false)
	@ColumnDefault("6")
	private Integer maxParticipants;

	@Column(nullable = false)
	@ColumnDefault("1")
	private Integer currentParticipants;

	@Column(nullable = false, length = 2048)
	private String fixtureUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fixture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member host;

	@Setter
	@OneToOne(mappedBy = "party", fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;

	public static Party createParty(String title, String fixtureUrl, Fixture fixture, Member host, Club club) {
		Party party = new Party();
		party.title = title;
		party.fixtureUrl = fixtureUrl;
		party.fixture = fixture;
		party.host = host;
		party.club = club;
		return party;
	}
}


