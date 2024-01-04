package com.example.obledapi.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "command_id", nullable = false)
    private Long commandId;
    private String userFirstName;
    private String userLastName;
    private String address;
    private String userEmail;
    private String ville;
    private String postCode;
    private String phone;
}
