package com.example.obledapi.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_detail_id", nullable = false)
    private Long fileDetailId;

    @Column private String fileName;
    @Column private String fileUri;
    @Column private String fileDownloadUri;
    @Column private long fileSize;
    @Column private String uploaderName;
}
