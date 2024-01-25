package com.spoparty.api.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.entity.Notification;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.api.member.repository.NotificationRepository;
import com.spoparty.api.member.repository.projection.NotificationProjection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final MemberRepository memberRepository;

	public List<NotificationProjection> getNotificationList(Long memberId) {
		return notificationRepository.findByMember_id(memberId, NotificationProjection.class);
	}

	public NotificationProjection registerNotification(Notification notification) {
		Member member = memberRepository.findById(notification.getMember().getId(), Member.class).orElse(null);
		if (member == null)
			return null;
		notification.setMember(member);
		Notification tmp = notificationRepository.save(notification);
		return notificationRepository.findById(tmp.getId(), NotificationProjection.class).orElse(null);
	}

	@Transactional
	public NotificationProjection updateNotificationState(Long notificationId, int state) {
		Notification data = notificationRepository.findById(notificationId, Notification.class).orElse(null);
		if (data == null)
			return null;

		data.setState(state);
		return notificationRepository.findById(notificationId, NotificationProjection.class).orElse(null);
	}

}
