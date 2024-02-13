package com.spoparty.batch.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.ToString;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = 0")
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
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fixture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Fixture fixture;

	@OneToMany(mappedBy = "cheerFixture")
	private List<Cheer> cheers = new ArrayList<>();

	@Builder
	public CheerFixture(Fixture fixture, long homeCount, long awayCount) {
		this.fixture = fixture;
		this.homeCount = homeCount;
		this.awayCount = awayCount;
	}
}
