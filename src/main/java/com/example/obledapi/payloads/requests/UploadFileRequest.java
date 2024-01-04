package com.example.obledapi.payloads.requests;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadFileRequest {
    private String name;
    private MultipartFile file;
}
