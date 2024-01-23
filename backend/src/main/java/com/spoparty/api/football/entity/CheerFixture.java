package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.spoparty.api.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class CheerFixture extends BaseEntity {
	@Id
	@GeneratedValue
	@Column(name = "cheer_fixture_id")
	private long id;

	@Column(nullable = false)
	private long home_count;

	@Column(nullable = false)
	private long away_count;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fixture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@OneToMany(mappedBy = "cheerFixture")
	private List<Cheer> cheers = new ArrayList<>();
}
