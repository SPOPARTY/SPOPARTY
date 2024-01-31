package com.spoparty.api.member.controller;

import static com.spoparty.api.common.constants.ErrorCode.*;
import static com.spoparty.api.common.constants.SuccessCode.*;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.common.exception.UnauthorizedException;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.entity.Notification;
import com.spoparty.api.member.entity.NotificationProjection;
import com.spoparty.api.member.service.NotificationService;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping("/{memberId}")
	public ResponseEntity<?> getNotificationList(@PathVariable("memberId") Long memberId) {
		List<NotificationProjection> list = notificationService.getNotificationList(memberId);
		if (list.isEmpty())
			return ApiResponse.error(DATA_NOT_FOUND);
		else
			return ApiResponse.success(GET_SUCCESS, list);
	}

	@PostMapping
	public ResponseEntity<?> registerNotification(@RequestBody Notification notification) {
		log.info(notification.toString());
		NotificationProjection data = notificationService.registerNotification(notification);
		if (data == null)
			return ApiResponse.error(EXAMPLE_ERROR);
		else
			return ApiResponse.success(CREATE_SUCCESS, data);
	}

	@PutMapping("/{notificationId}")
	public ResponseEntity<?> readNotification(@PathVariable("notificationId") Long notificationId) {
		NotificationProjection data = notificationService.updateNotificationState(notificationId, 1);
		if (data == null)
			return ApiResponse.error(DATA_NOT_FOUND);
		else
			return ApiResponse.success(UPDATE_SUCCESS, data);
	}

	@DeleteMapping("/{notificationId}")
	public ResponseEntity<?> deleteNotification(@PathVariable("notificationId") Long notificationId) {
		NotificationProjection data = notificationService.updateNotificationState(notificationId, 2);
		if (data == null)
			return ApiResponse.success(DELETE_SUCCESS, null);
		else
			return ApiResponse.error(EXAMPLE_ERROR);
	}

	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> connect(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		if (principalDetails == null)
			throw new UnauthorizedException(UNAUTHORIZED_USER);
		Member member = principalDetails.getMember();
		SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);
		notificationService.add(member.getId(), emitter);
		try {
			emitter.send(SseEmitter.event()
				.name("connect")
				.data("connected!"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.status(200).body(emitter);
	}

	// @GetMapping("/push/{message}")
	// public ResponseEntity<?> push(@PathVariable("message") String msg) {
	// 	Notification notification = new Notification();
	// 	notification.setId(2L);
	// 	notification.setTitle(msg);
	// 	notification.setContent(msg);
	// 	notificationService.push(notification);
	// 	return ResponseEntity.status(200).body(notification);
	// }

}
