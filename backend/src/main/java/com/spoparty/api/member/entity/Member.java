package com.spoparty.api.member.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(nullable = false)
	private String loginId;

	@Column(nullable = false)
	private String loginPwd;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false, columnDefinition = "varchar(255) default 'basic'")
	private String provider;

	@Column(nullable = false, columnDefinition = "varchar(255) default 'ROLE_USER'")
	private String roleName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teamId", referencedColumnName = "teamId")
	private Team teamInfo;

	@Column(nullable = false, columnDefinition = "varchar(255) default 0")
	private int status;

	@CreationTimestamp
	private LocalDateTime createdTime;
	@UpdateTimestamp
	private LocalDateTime updatedTime;
}
