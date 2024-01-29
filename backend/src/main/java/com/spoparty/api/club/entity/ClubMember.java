package com.spoparty.api.club.entity;

import static com.spoparty.api.common.constants.ErrorCode.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import com.spoparty.api.common.entity.BaseEntity;
import com.spoparty.api.common.entity.RoleType;
import com.spoparty.api.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "is_deleted = 0")
public class ClubMember extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_member_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Club club;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	@Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoleType role;

	// 생성 메서드
	public static ClubMember createClubMember(Member member, Club club, RoleType role) {
		ClubMember clubMember = new ClubMember();
		clubMember.member = member;
		clubMember.club = club;
		clubMember.role = role;
		club.addClubMember(clubMember);
		return clubMember;
	}

	// 비즈니스 로직
	public void deleteClubMember(Club club) {
		if (club.getCurrentParticipants() == 1) { // 그룹에 남은 인원이 없는 경우 그룹은 삭제됨
			club.deleteClub();
			return;
		}
		if (role.equals(RoleType.host)) { // 그룹원이 남아있는데 그룹을 나가는 경우
			throw new IllegalArgumentException(HOST_CANNOT_LEAVE_GROUP.getMessage());
		}
		this.softDelete();
		club.removeClubMember(this);
	}
}
