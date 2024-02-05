package com.spoparty.api.openvidu;

import static com.spoparty.api.common.constants.ErrorCode.*;
import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spoparty.api.common.dto.ApiResponse;
import com.spoparty.api.member.entity.Member;
import com.spoparty.security.model.PrincipalDetails;

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
@RestController
@RequestMapping("/openvidu")
public class OpenViduController {
	@Value("${openvidu.url}")
	private String OPENVIDU_URL;

	@Value("${openvidu.secret}")
	private String OPENVIDU_SECRET;

	private OpenVidu openvidu;

	@PostConstruct
	public void init() {
		this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
	}

	@PostMapping("/hello")
	public ResponseEntity<?> hello()
		throws OpenViduJavaClientException, OpenViduHttpException {
		return ApiResponse.success(GET_SUCCESS, null);
	}

	@PostMapping("/sessions")
	public ResponseEntity<?> initializeSession(@RequestBody(required = false) Map<String, Object> params)
		throws OpenViduJavaClientException, OpenViduHttpException {
		SessionProperties properties = SessionProperties.fromJson(params).build();
		Session session = openvidu.createSession(properties);
		log.debug(("/api/session called"));
		log.debug("params : {}", params);
		log.debug("SessionProperties : {}", properties);
		log.debug("Session : {}", session);
		return ApiResponse.success(GET_SUCCESS, session.getSessionId());
	}

	@PostMapping("/sessions/{sessionId}/connections")
	public ResponseEntity<?> createConnection(@PathVariable("sessionId") String sessionId,
		@RequestBody(required = false) Map<String, Object> params)
		throws OpenViduJavaClientException, OpenViduHttpException {
		log.debug("/api/sessionId/connections called");
		Session session = openvidu.getActiveSession(sessionId);
		if (session == null) {
			log.debug(("session is null"));
			return ApiResponse.error(DATA_NOT_FOUND);
		}
		ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
		Connection connection = session.createConnection(properties);
		log.debug("params : {}", params);
		log.debug("Session : {}", session);
		log.debug("ConnectionProperties : {}", properties);
		log.debug("Connection : {}", connection);
		return ApiResponse.success(GET_SUCCESS, connection.getToken());
	}

	// @GetMapping("/sessions/{sessionId}")
	// public ResponseEntity<?> getSession(@PathVariable("sessionId") String sessionId) {
	//
	//     Session session = openvidu.getActiveSession()
	//     return ResponseEntity.status(HttpStatus.OK).body()
	// }
}
