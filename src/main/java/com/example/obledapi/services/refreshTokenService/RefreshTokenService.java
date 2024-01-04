package com.example.obledapi.services.refreshTokenService;

import com.example.obledapi.Entities.RefreshToken;
import com.example.obledapi.payloads.responses.UserMachineDetails;

import java.util.Optional;

public interface RefreshTokenService {
    public Optional<RefreshToken> findByToken(String token);
    public RefreshToken createRefreshToken(String email, UserMachineDetails userMachineDetails);
    public RefreshToken verifyExpiration(RefreshToken token);
    public void deleteByUserId(String email);
}
