package com.example.obledapi.payloads.responses;

import com.example.obledapi.Entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private Users user;
    private String token;
    private String refreshToken;
    private String tokenType;
    private String firstName;
}
