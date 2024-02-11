package com.spoparty.api.member.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final String PREFIX = "<!DOCTYPE html>\n"
		+ "<html>\n"
		+ "<body>\n"
		+ "    <div class=\"container\">\n"
		+ "        <div class=\"header\">\n"
		+ "            <h2>이메일 인증</h2>\n"
		+ "        </div>\n"
		+ "        <div class=\"content\">\n"
		+ "            <p>아래의 인증번호를 입력하여 이메일 인증을 완료해 주세요.</p>\n"
		+ "            <p class=\"code\">";
	private final String SUFFIX = "</p>\n"
		+ "            <p>인증번호는 5분 동안 유효합니다.</p>\n"
		+ "        </div>\n"
		+ "    </div>\n"
		+ "</body>\n"
		+ "</html>";
	private final String SUBJECT;
	private final Map<String, Integer> codes;

	@Autowired
	public EmailService(JavaMailSender javaMailSender, @Value("${smtp.mail.subject}") String SUBJECT,
		@Value("${smtp.mail.prefix}") String PREFIX, @Value("${smtp.mail.suffix}") String SUFFIX) {
		this.javaMailSender = javaMailSender;
		codes = new ConcurrentHashMap<>();
		this.SUBJECT = SUBJECT;
		// this.PREFIX = PREFIX;
		// this.SUFFIX = SUFFIX;
		log.info("EmailService 생성");
	}

	public void sendEmailCode(String to) throws Exception {
		int code = setCode(to);
		sendEmail(to, SUBJECT, PREFIX + code + SUFFIX);
	}

	public void sendTmpPwd(String to, int code) throws Exception {
		String s1 = "<!DOCTYPE html>\n"
			+ "<html>\n"
			+ "<body>\n"
			+ "    <div class=\"container\">\n"
			+ "        <div class=\"header\">\n"
			+ "            <h2>임시 비밀번호 발급</h2>\n"
			+ "        </div>\n"
			+ "        <div class=\"content\">\n"
			+ "            <p>아래의 임시 비밀번호로 로그인 후, 보안을 위해 비밀번호를 변경해 주세요.</p>\n"
			+ "            <p class=\"code\">";
		String s2 = "</p>\n"
			+ "        </div>\n"
			+ "    </div>\n"
			+ "</body>\n"
			+ "</html>";
		sendEmail(to, "SPOPARTY 비밀번호 찾기", s1 + code + s2);
	}

	public void sendEmail(String to, String subject, String msg) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(msg, true);  // true를 설정하여 HTML을 지원하도록 합니다.
		log.info("EmailService.sendHtmlEmail() : " + mimeMessage);
		javaMailSender.send(mimeMessage);
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
