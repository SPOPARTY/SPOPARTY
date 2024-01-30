package com.spoparty.api.common.constants;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
	// 일반
	EXAMPLE_SUCCESS(OK, "예시 성공"),
	GET_SUCCESS(OK, "조회 성공"),
	CREATE_SUCCESS(CREATED, "생성 성공"),
	UPDATE_SUCCESS(OK, "수정 성공"),
	DELETE_SUCCESS(OK, "삭제 성공"),

	// 그룹
	CLUB_CREATE_SUCCESS(OK, "그룹 생성 성공"),
	CLUB_UPDATE_SUCCESS(OK, "그룹 수정 성공"),
	CLUB_DELETE_SUCCESS(OK, "그룹 삭제 성공"),
	CLUB_READ_SUCCESS(OK, "그룹 조회 성공"),
	INVITE_URL_GET_SUCCESS(OK, "초대 링크 반환 성공"),
	CLUB_MEMBERS_READ_SUCCESS(OK, "그룹원 목록 조회 성공"),
	CLUB_MEMBER_CREATE_SUCCESS(OK, "그룹원 생성 성공"),
	CLUB_HOST_UPDATE_SUCCESS(OK, "그룹장 수정 성공"),
	CLUB_MEMBER_DELETE_SUCCESS(OK, "그룹원 삭제 성공"),

	// 축구
	CHEER_CREATE_SUCCESS(CREATED, "응원 정보 생성 성공");

	private final HttpStatus status;
	private final String message;
}
