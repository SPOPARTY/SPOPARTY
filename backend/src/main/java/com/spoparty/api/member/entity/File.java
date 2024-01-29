package com.spoparty.api.member.entity;

import com.spoparty.api.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class File extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;

	@Column(nullable = false, length = 20)
	private String type = "";

	@Column(nullable = false, length = 100)
	private String url = "";

	@Column(nullable = false, length = 100)
	private String thumbnailUrl = "";

}
