package com.spoparty.api.club.dto.response;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.querydsl.core.annotations.QueryProjection;
import com.spoparty.api.club.entity.Club;
import com.spoparty.api.member.entity.Member;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class ClubResponseDTO {
	private Long clubId;
	private String name;
	private Integer maxParticipants;
	private Integer currentParticipants;
	private Long partyId;
	private Long hostId;
	private String hostName;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedTime;

	@QueryProjection
	public ClubResponseDTO(Club entity, int currentParticipants) {
		this.clubId = entity.getId();
		this.name = entity.getName();
		this.maxParticipants = entity.getMaxParticipants();
		this.currentParticipants = currentParticipants;
		this.createdTime = entity.getCreatedTime();
		this.updatedTime = entity.getUpdatedTime();

		try {
			Member hostMember = entity.getHostMember();
			hostId = hostMember.getId();
			hostName = hostMember.getNickname();
		} catch (EntityNotFoundException e) {
			log.debug("entity.getHostMember() EntityNotFoundException 발생!!!");
			hostId = null;
			hostName = null;
		}

		if (entity.getParty() != null) {
			partyId = entity.getParty().getId();
		}
	}
}
