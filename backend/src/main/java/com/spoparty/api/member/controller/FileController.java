package com.spoparty.api.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/files")
public class FileController {

	private final AmazonS3 amazonS3;
	private final FileRepository fileRepository;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.region.static}")
	private String region;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		List<File> list = new ArrayList<>();
		try {
			for (MultipartFile file : files) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(file.getContentType());
				metadata.setContentLength(file.getSize());
				amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

				File saveFile = new File();
				saveFile.setType("image");
				saveFile.setUrl("https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName);
				list.add(saveFile);
			}
			fileRepository.saveAll(list);
			return ResponseEntity.status(201).body(list);
		} catch (IOException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(400).body(null);
		}
	}

	@GetMapping("/{type}")
	public ResponseEntity<?> findFileByType(@PathVariable("type") String type) {
		List<File> list = fileRepository.findByType(type, File.class);
		return ResponseEntity.status(200).body(list);
	}

}
