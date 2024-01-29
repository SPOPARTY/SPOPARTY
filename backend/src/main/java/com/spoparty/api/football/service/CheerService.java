package com.spoparty.api.football.service;

import com.spoparty.api.football.response.ResponseDTO;
import com.spoparty.security.model.PrincipalDetails;

public interface CheerService {

	public void deleteEndCheerFixture();

	public ResponseDTO findCheerFixture(PrincipalDetails principalDetails);

	public long makeCheer(int memberId, int cheerFixtureId, int teamId);
}
