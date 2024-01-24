package com.spoparty.api.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.member.entity.Notification;
import com.spoparty.api.member.repository.projection.NotificationProjection;
import com.spoparty.api.member.service.NotificationService;

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
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(200).body(list);
	}

	@PostMapping
	public ResponseEntity<?> registerNotification(@RequestBody Notification notification) {
		NotificationProjection data = notificationService.registerNotification(notification);
		if (data == null)
			return ResponseEntity.status(400).body(null);
		else
			return ResponseEntity.status(201).body(data);
	}

	@PutMapping("/{notificationId}")
	public ResponseEntity<?> readNotification(@PathVariable("notificationId") Long notificationId) {
		NotificationProjection data = notificationService.updateNotificationState(notificationId, 1);
		if (data == null)
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(200).body(data);
	}

	@DeleteMapping("/{notificationId}")
	public ResponseEntity<?> deleteNotification(@PathVariable("notificationId") Long notificationId) {
		NotificationProjection data = notificationService.updateNotificationState(notificationId, 2);
		if (data == null)
			return ResponseEntity.status(404).body(null);
		else
			return ResponseEntity.status(200).body(data);
	}

}
