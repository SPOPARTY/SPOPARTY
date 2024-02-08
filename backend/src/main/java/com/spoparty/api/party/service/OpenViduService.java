package com.spoparty.api.party.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpenViduService {

	@Value("${openvidu.url}")
	private String OPENVIDU_URL;

	@Value("${openvidu.secret}")
	private String OPENVIDU_SECRET;

	private OpenVidu openvidu;

	@PostConstruct
	public void init() {
		this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
	}

	public String initializeSession(Map<String, Object> params) throws
		OpenViduJavaClientException, OpenViduHttpException {
		log.debug("initializeSession 시작 - {}", params);
		SessionProperties properties = SessionProperties.fromJson(params).build();
		Session session = openvidu.createSession(properties);

		return session.getSessionId();
	}

	public String createConnection(String sessionId, Map<String, Object> params) throws
		OpenViduJavaClientException, OpenViduHttpException {
		log.debug("createConnection 시작 - {}", params);
		Session session = openvidu.getActiveSession(sessionId);
		if (session == null) {
			log.debug("session is null");
			return null;
		}
		ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
		Connection connection = session.createConnection(properties);

		return connection.getToken();
	}
}
