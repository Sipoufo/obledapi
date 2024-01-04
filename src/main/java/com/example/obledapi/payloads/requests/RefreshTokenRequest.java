package com.example.obledapi.payloads.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenRequest {
    private String refreshToken;
}
