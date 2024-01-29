package com.spoparty.api.openvidu;



import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequestMapping("/openvidu")
@RestController
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
    public ResponseEntity<String> hello()
            throws OpenViduJavaClientException, OpenViduHttpException {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        log.debug(("/api/session called"));
        log.debug("params : {}", params);
        log.debug("SessionProperties : {}", properties);
        log.debug("Session : {}", session);
        return ResponseEntity.status(HttpStatus.OK).body(session.getSessionId());
    }

    @PostMapping("/api/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        log.debug(("/api/sessionId/connections called"));
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            log.debug(("session is null"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        log.debug("params : {}", params);
        log.debug("Session : {}", session);
        log.debug("ConnectionProperties : {}", properties);
        log.debug("Connection : {}", connection);
        return ResponseEntity.status(HttpStatus.OK).body(connection.getToken());
    }
}
