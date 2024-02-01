package com.spoparty.api.member.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.entity.Notification;
import com.spoparty.api.member.entity.NotificationProjection;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.api.member.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final MemberRepository memberRepository;
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	public List<NotificationProjection> getNotificationList(Long memberId) {
		return notificationRepository.findByMember_id(memberId, NotificationProjection.class);
	}

	public NotificationProjection registerNotification(Notification notification) {
		Member member = memberRepository.findById(notification.getMember().getId(), Member.class)
			.orElseThrow(() -> new CustomException(
				DATA_NOT_FOUND));
		notification.setMember(member);
		Notification tmp = notificationRepository.save(notification);
		push(notification);
		return notificationRepository.findById(tmp.getId(), NotificationProjection.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
	}

	@Transactional
	public NotificationProjection updateNotificationState(Long notificationId, int state) {
		Notification data = notificationRepository.findById(notificationId, Notification.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
		data.setState(state);
		return notificationRepository.findById(notificationId, NotificationProjection.class)
			.orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
	}

	public void add(Long memberId, SseEmitter emitter) {
		emitters.put(memberId, emitter);
		log.info("new emitter added: {}", emitter);
		log.info("emitters: {}", emitters);
		emitter.onCompletion(() -> {
			this.emitters.remove(memberId);    // 만료되면 리스트에서 삭제
		});
		emitter.onTimeout(() -> {
			emitter.complete();
		});
	}

	public void push(Notification notification) {
		SseEmitter emitter = emitters.get(notification.getId());
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
					.name("notification")
					.data(notification));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
