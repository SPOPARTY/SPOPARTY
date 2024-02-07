package com.spoparty.api.football.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spoparty.api.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheerFixture extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cheer_fixture_id")
	private long id;

	@Column(nullable = false)
	private long homeCount;

	@Column(nullable = false)
	private long awayCount;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fixture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@OneToMany(mappedBy = "cheerFixture")
	private List<Cheer> cheers = new ArrayList<>();


	public void cheerHome(){
		homeCount++;
	}

	public void cheerAway() {
		awayCount++;
	}
	@Builder
	public CheerFixture(long homeCount, long awayCount) {
		this.homeCount = homeCount;
		this.awayCount = awayCount;
		// this.fixture = fixture;
	}
}
