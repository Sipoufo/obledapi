package com.example.obledapi.payloads.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenVerifyResponse {
    private boolean isValid;
    private String message;
}
