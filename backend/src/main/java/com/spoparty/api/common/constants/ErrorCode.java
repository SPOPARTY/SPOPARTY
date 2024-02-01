package com.spoparty.api.common.constants;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 대표 Error
	BAD_CLIENT_REQUEST(BAD_REQUEST, "잘못된 요청 입니다."),
	DATA_NOT_FOUND(NOT_FOUND, "데이터를 찾을 수 없습니다."),
	CONFLICT_DATA(CONFLICT, "중복된 데이터 입니다."),
	SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류로 인해 응답을 제공할 수 없습니다."),

	/* 400 BAD_REQUEST: 잘못된 요청 구문 */
	// 일반
	EXAMPLE_ERROR(BAD_REQUEST, "예시 실패"),
	NO_ID(BAD_REQUEST, "존재하지 않는 id 입니다"),
	ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 값입니다"),
	BAD_PARAMETER(BAD_REQUEST, "요청 파라미터가 잘못되었습니다."),
	BAD_PARAMETER_TYPE(BAD_REQUEST, "지원하지 않는 파라미터 형식입니다."),

	// 그룹
	NOT_ENOUGH_GROUP_PARTICIPANTS(BAD_REQUEST, "그룹에 회원이 없습니다."),
	ENOUGH_GROUP_PARTICIPANTS(BAD_REQUEST, "그룹에 회원이 꽉 찼습니다."),
	NO_GROUP_MEMBER(BAD_REQUEST, "존재하지 않은 그룹원입니다."),
	ALREADY_GROUP_MEMBER(BAD_REQUEST, "이미 존재하는 그룹원입니다."),

	INVALID_INVITE_URL(BAD_REQUEST, "유효하지 않은 초대 링크입니다."),
	NO_GROUP_ID(BAD_REQUEST, "존재하지 않거나 삭제된 그룹입니다."),
	HOST_CANNOT_LEAVE_GROUP(BAD_REQUEST, "그룹장은 그룹을 나갈 수 없습니다. 그룹장을 넘기세요."),

	/* 400 BadRequest: 클라이언트에서 보낸 정보가 잘못됨 */
	CANNOT_CREATE_CHEER(BAD_REQUEST, "응원 생성에 실패하였습니다."),

	/* 401 UNAUTHORIZED: 인증 실패 */
	UNAUTHORIZED_USER(UNAUTHORIZED, "만료되었거나 잘못된 토큰입니다. 토큰을 확인해주세요."),

	/* 403 FORBIDDEN: 권한 없음 */
	FORBIDDEN_USER(FORBIDDEN, "접근 권한이 없는 유저입니다."),

	/* 404 NOT_FOUND: 리소스를 찾을 수 없음 */
	USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),
	TOKEN_NOT_FOUND(NOT_FOUND, "다시 로그인해주세요."),
	PROFILE_NOT_FOUND(NOT_FOUND, "유저의 프로필 정보가 존재하지 않습니다."),

	/* 500 INTERNAL_SERVER_ERROR : 서버 오류 */
	ENCRYPT_FAIL(INTERNAL_SERVER_ERROR, "암호화에 실패했습니다."),
	DECRYPT_FAIL(INTERNAL_SERVER_ERROR, "복호화에 실패했습니다."),
	FILE_UPLOAD_FAIL(INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");

	private final HttpStatus status;
	private final String message;
}
