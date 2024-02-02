package com.spoparty.api.party.dto;

import com.spoparty.redis.DataType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RedisDataDto<T> {
	DataType dataType;
	T data;
}
