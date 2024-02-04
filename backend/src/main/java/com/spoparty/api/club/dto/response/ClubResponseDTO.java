package com.spoparty.api.club.dto.response;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.querydsl.core.annotations.QueryProjection;
import com.spoparty.api.club.entity.Club;

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
	private String hostName;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedTime;

	@QueryProjection
	public ClubResponseDTO (Club entity, int currentParticipants) {
		// log.debug("ClubResponseDTO - entity: {}, currentParticipants: {}", entity, currentParticipants);
		// log.debug("clubId - {}, name - {}, party - {}, host - {}", entity.getId(), entity.getName(), entity.getParty(), entity.getHostMember());
		this.clubId = entity.getId();
		this.name = entity.getName();
		this.maxParticipants = entity.getMaxParticipants();
		this.currentParticipants = currentParticipants;
		this.createdTime = entity.getCreatedTime();
		this.updatedTime = entity.getUpdatedTime();

		if (entity.getHostMember() != null) {
			log.debug(">>>>>>>>>>>>>>");
			log.debug("host 정보 - {}", entity.getHostMember());
			hostName = entity.getHostMember().getNickname();
		}

		if (entity.getParty() != null) {
			partyId = entity.getParty().getId();
		}
	}
}
