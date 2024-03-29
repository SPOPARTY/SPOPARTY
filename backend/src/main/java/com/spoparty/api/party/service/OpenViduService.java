package com.spoparty.api.party.service;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.service.FileService;
import com.spoparty.api.party.dto.response.RecordingResponseDTO;
import com.spoparty.common.util.CustomMultipartFile;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenViduService {

	@Value("${openvidu.url}")
	private String OPENVIDU_URL;

	@Value("${openvidu.secret}")
	private String OPENVIDU_SECRET;

	private OpenVidu openvidu;

	private Map<String, Boolean> sessionRecordings = new ConcurrentHashMap<>();

	private final String RECORDINGS_PATH = "/opt/openvidu/recordings/";

	private final FileService fileService;
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

	public String createConnection(String sessionId, Map<String, Object> params, Long memberId) throws
		OpenViduJavaClientException, OpenViduHttpException {
		log.debug("createConnection 시작 - {}", params);
		Session session = openvidu.getActiveSession(sessionId);
		if (session == null) {
			log.debug("session is null");
			return null;
		}
		ConnectionProperties properties = ConnectionProperties.fromJson(params).data(String.valueOf(memberId)).build();
		Connection connection = session.createConnection(properties);

		return connection.getToken();
	}
	public Recording startRecording(String sessionId, Map<String, Object> params) throws
			OpenViduJavaClientException, OpenViduHttpException {

		Recording.OutputMode outputMode = Recording.OutputMode.valueOf((String) params.get("outputMode"));
		boolean hasAudio = (boolean) params.get("hasAudio");
		boolean hasVideo = (boolean) params.get("hasVideo");

		RecordingProperties properties = new RecordingProperties.Builder().outputMode(outputMode).hasAudio(hasAudio)
				.hasVideo(hasVideo).frameRate(4).build();

		log.info("Starting recording for session {} with properties outputMode= {}, hasAudio= {}, hasVideo={}", sessionId, outputMode, hasAudio, hasVideo);

		Recording recording = openvidu.startRecording(sessionId, properties);

		sessionRecordings.put(sessionId, true);
		return recording;
	}

	public void stopRecording(String recordingId) throws
			OpenViduJavaClientException, OpenViduHttpException {

		log.info("Stoping recording | {}", recordingId);

		Recording recording = openvidu.stopRecording(recordingId);
		sessionRecordings.remove(recording.getSessionId());
	}

	public RecordingResponseDTO uploadRecording(String recordingId) throws InvalidPathException {
		Path videoPath = Paths.get(String.format("%s/%s/%s.mp4", RECORDINGS_PATH, recordingId, recordingId));
		Path thumbnailPath = Paths.get(String.format("%s/%s/%s.jpg", RECORDINGS_PATH, recordingId, recordingId));

		CustomMultipartFile videoFile = new CustomMultipartFile(videoPath.toFile());
		CustomMultipartFile thumbnailFile = new CustomMultipartFile(thumbnailPath.toFile());

		File uploadedVideoFile = fileService.uploadFile(videoFile);
		File uploadedThumbnailFile = fileService.uploadFile(thumbnailFile);

		RecordingResponseDTO recordingResponseDTO = RecordingResponseDTO
				.builder()
				.id(uploadedVideoFile.getId())
				.thumbnailId(uploadedThumbnailFile.getId())
				.url(uploadedVideoFile.getUrl())
				.thumbnailUrl(uploadedThumbnailFile.getUrl())
				.build();

		return recordingResponseDTO;
	}
	public void deleteRecording(String recordingId) throws IOException {
		Path path = Paths.get(String.format("%s/%s", RECORDINGS_PATH, recordingId));

		for (java.io.File f: path.toFile().listFiles()) {
			f.delete();
		}
		path.toFile().delete();

		log.info("file deleted : {}", path);
	}
}
