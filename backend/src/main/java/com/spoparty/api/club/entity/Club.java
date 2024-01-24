package com.spoparty.api.club.entity;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.spoparty.api.common.constants.ErrorCode;
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
import lombok.Getter;

@Entity
@Getter
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

	@OneToMany
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private List<Member> members;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "party_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Party party;
}
