package com.spoparty.api.member.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final String PREFIX;
	private final String SUFFIX;
	private final String SUBJECT;
	private final Map<String, Integer> codes;

	@Autowired
	public EmailService(JavaMailSender javaMailSender, @Value("${smtp.mail.subject}") String SUBJECT,
		@Value("${smtp.mail.prefix}") String PREFIX, @Value("${smtp.mail.suffix}") String SUFFIX) {
		this.javaMailSender = javaMailSender;
		codes = new ConcurrentHashMap<>();
		this.SUBJECT = SUBJECT;
		this.PREFIX = PREFIX;
		this.SUFFIX = SUFFIX;
		log.info("EmailService 생성");
	}

	public void sendEmailCode(String to) throws MailSendException, InterruptedException {
		int code = setCode(to);
		sendEmail(to, SUBJECT, PREFIX + code + SUFFIX);
	}

	public void sendEmail(String to, String subject, String msg) throws MailSendException, InterruptedException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(msg);
		log.info("EmailService.sendEmail() : " + mailMessage);
		javaMailSender.send(mailMessage);
	}

	public int setCode(String to) {
		int code = (int)(Math.random() * 1000000);
		codes.put(to, code);
		log.info("EmailService.setCode: " + codes);
		return code;
	}

	public boolean checkCode(String to, Integer code) {
		Integer c = codes.get(to);
		if (c == null || !c.equals(code))
			return false;
		codes.remove(to);
		return true;
	}

}
