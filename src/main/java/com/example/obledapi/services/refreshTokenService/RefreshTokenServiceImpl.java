package com.example.obledapi.services.refreshTokenService;

import com.example.obledapi.Entities.RefreshToken;
import com.example.obledapi.Entities.Users;
import com.example.obledapi.payloads.responses.UserMachineDetails;
import com.example.obledapi.repositories.RefreshTokenRepository;
import com.example.obledapi.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final Long refreshTokenDurationMs = 2592000000L;
    @Autowired
    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(String email, UserMachineDetails userMachineDetails) {
        Users user = userService.getOneUserL(email);

        RefreshToken refreshToken = RefreshToken
                .builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .ipAddress(userMachineDetails.getIpAddress())
                .browser(userMachineDetails.getBrowser())
                .operatingSystem(userMachineDetails.getOperatingSystem())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return null;
            // throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Override
    @Transactional
    public void deleteByUserId(String email) {
        refreshTokenRepository.deleteByUser(userService.getOneUserL(email));
    }
}
