package com.spoparty.api.member.controller;

import static com.spoparty.api.common.constants.SuccessCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spoparty.api.common.dto.ApiResponse;
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

	@PostMapping
	public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		List<File> list = new ArrayList<>();
		for (MultipartFile file : files) {
			list.add(fileService.uploadFile(file));
		}
		return ApiResponse.success(CREATE_SUCCESS, list);
	}

	@GetMapping("/{type}")
	public ResponseEntity<?> findFileByType(@PathVariable("type") String type) {
		List<File> list = fileService.findByType(type);
		return ApiResponse.success(GET_SUCCESS, list);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteFile(@PathVariable("id") Long id) {
		fileService.deleteFile(id);
		return ApiResponse.success(DELETE_SUCCESS);
	}

	@PostMapping("/url")
	public ResponseEntity<?> uploadUrl(@RequestBody String url) {
		File file = fileService.uploadUrl(url);
		return ApiResponse.success(CREATE_SUCCESS, file);
	}

}
