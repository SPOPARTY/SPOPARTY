package com.spoparty.api.member.entity;

import org.hibernate.annotations.Where;

import com.spoparty.api.common.entity.FootballBaseEntity;
import com.spoparty.api.football.entity.Team;

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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Where(clause = "state in (0, 1)")
@Entity
public class Member extends FootballBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, length = 50)
	private String loginId;

	@Column(nullable = false, length = 100)
	private String loginPwd;

	@Column(nullable = false, length = 50)
	private String nickname;

	@Column(nullable = false, length = 50)
	private String email;

	@Column(nullable = false, length = 50)
	private String provider = "basic";

	@Column(nullable = false, length = 25)
	private String roleName = "ROLE_USER";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id", referencedColumnName = "team_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Team team;

	@Column(nullable = false)
	private int state = 0;

}
