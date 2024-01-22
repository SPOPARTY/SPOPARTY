package com.spoparty.api.member.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teamId;
	private String nameKr;
	private String nameEng;
	private String logo;

	@CreationTimestamp
	private LocalDateTime createdTime;
	@UpdateTimestamp
	private LocalDateTime updatedTime;

}
