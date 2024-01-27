package com.spoparty.api.board.entity;

import com.spoparty.api.common.entity.BaseEntity;
import com.spoparty.api.member.entity.File;
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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "club_id", referencedColumnName = "club_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	// private Club club;

	@Column(nullable = false, length = 50)
	private String title = "";

	@Column(nullable = false, length = 255)
	private String content = "";

	@OneToOne
	@JoinColumn(name = "file_id", referencedColumnName = "file_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private File file;

}
