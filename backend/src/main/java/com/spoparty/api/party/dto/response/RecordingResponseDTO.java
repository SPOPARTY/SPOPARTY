package com.spoparty.api.party.dto.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class RecordingResponseDTO {
    Long id;
    Long thumbnailId;
    String url;
    String thumbnailUrl;
}
