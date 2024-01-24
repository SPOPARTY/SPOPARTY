package com.spoparty.api.member.entity;

import com.spoparty.api.common.entity.FootballBaseEntity;
import com.spoparty.api.football.entity.Team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id", referencedColumnName = "team_id")
	private Team teamInfo;

	@Column(nullable = false, length = 25)
	private int status = 0;

}
