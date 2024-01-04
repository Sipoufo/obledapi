package com.example.obledapi.services.fileUploadService;

import com.example.obledapi.Entities.FileDetails;
import com.example.obledapi.payloads.responses.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileUploadService {
    public FileUploadResponse uploadFile(MultipartFile file, String uploaderName) throws IOException;
    public Resource fetchFileAsResource(String fileName) throws FileNotFoundException;
    public List<FileDetails> getAllFiles();
}
