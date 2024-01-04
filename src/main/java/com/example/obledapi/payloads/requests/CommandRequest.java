package com.example.obledapi.payloads.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommandRequest {
    private String userFirstName;
    private String userLastName;
    private String address;
    private String userEmail;
    private String postCode;
    private String phone;
    private String ville;
}
