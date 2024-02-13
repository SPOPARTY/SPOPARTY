package com.spoparty.api.member.service;

import static com.spoparty.api.common.constants.ErrorCode.*;

import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spoparty.api.common.exception.CustomException;
import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

	private final AmazonS3 amazonS3;
	private final FileRepository fileRepository;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.region.static}")
	private String region;

	public File uploadFile(MultipartFile file) {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
		} catch (Exception e) {
			log.error("FILE_UPLOAD_FAIL : {}", e);
			throw new CustomException(FILE_UPLOAD_FAIL);
		}

		File saveFile = new File();
		if (file.getOriginalFilename().split("\\.")[1].equals("mp4")) {
			saveFile.setType("video");
		} else {
			saveFile.setType("image");
		}
		saveFile.setUrl("https://" + bucket + ".s3." + region + ".amazonaws.com/" + URLEncoder.encode(fileName));
		return fileRepository.save(saveFile);
	}

	@Transactional
	public void deleteFile(Long id) {
		File file = fileRepository.findById(id, File.class)
			.orElse(null);
		if (file == null) {
			log.error("file not exist");
			return;
		}
		try {
			String fileName = file.getUrl().split("/")[3];
			log.info("delete file: {}", fileName);
			amazonS3.deleteObject(bucket, fileName);
		} catch (Exception e) {
			log.error("BAD_CLIENT_REQUEST : {}", e);
			throw new CustomException(BAD_CLIENT_REQUEST);
		}
		file.setUrl("deleted");
		file.softDelete();
	}

	public File uploadUrl(String url) {
		File file = new File();
		long time = System.currentTimeMillis();
		file.setUrl("https://" + bucket + ".s3." + region + ".amazonaws.com/" + time);
		file.setType("image");
		return fileRepository.save(file);
	}

	public List<File> findByType(String type) {
		return fileRepository.findByType(type, File.class);
	}

	public File findById(Long id) {
		return fileRepository.findById(id, File.class).orElse(null);
	}

}
