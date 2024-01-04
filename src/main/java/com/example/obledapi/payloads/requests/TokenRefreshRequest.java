package com.example.obledapi.payloads.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenRefreshRequest {
    private String refreshToken;
}