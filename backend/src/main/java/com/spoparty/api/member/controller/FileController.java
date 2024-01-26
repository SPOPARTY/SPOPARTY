package com.spoparty.api.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spoparty.api.member.entity.File;
import com.spoparty.api.member.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/files")
public class FileController {

	private final FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		List<File> list = new ArrayList<>();
		try {
			for (MultipartFile file : files) {
				list.add(fileService.uploadFile(file));
			}
			return ResponseEntity.status(201).body(list);
		} catch (IOException e) {
			return ResponseEntity.status(400).body(null);
		}
	}

	@GetMapping("/{type}")
	public ResponseEntity<?> findFileByType(@PathVariable("type") String type) {
		List<File> list = fileService.findByType(type);
		return ResponseEntity.status(200).body(list);
	}

}
