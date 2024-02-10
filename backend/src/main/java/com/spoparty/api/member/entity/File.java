package com.spoparty.api.member.entity;

import org.hibernate.annotations.Where;

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
@Where(clause = "is_deleted = 0")
@Entity
public class File extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;

	@Column(nullable = false, length = 20)
	private String type = "";

	@Column(nullable = false, length = 200)
	private String url = "";

	@Column(nullable = false, length = 200)
	private String thumbnailUrl = "";

}
