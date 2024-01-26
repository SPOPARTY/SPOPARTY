package com.spoparty.api.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.api.member.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

	<T> Optional<T> findById(Long id, Class<T> type);

}
