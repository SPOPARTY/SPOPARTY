package com.spoparty.api.member.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spoparty.api.common.constants.ErrorCode;
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
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
		}

		File saveFile = new File();
		saveFile.setType("image");
		saveFile.setUrl("https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName);
		return fileRepository.save(saveFile);
	}

	public List<File> findByType(String type) {
		return fileRepository.findByType(type, File.class);
	}

	public File findById(Long id) {
		return fileRepository.findById(id, File.class).orElse(null);
	}

}
