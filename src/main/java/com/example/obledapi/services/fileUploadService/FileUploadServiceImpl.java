package com.example.obledapi.services.fileUploadService;

import com.example.obledapi.Entities.FileDetails;
import com.example.obledapi.exceptions.FileNotSupportedException;
import com.example.obledapi.payloads.responses.FileUploadResponse;
import com.example.obledapi.repositories.FileDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    public FileUploadServiceImpl() throws IOException {}

    @Autowired
    private FileDetailRepository fileDetailsRepository;

    private final Path UPLOAD_PATH =
            Paths.get(new ClassPathResource("").getFile().getAbsolutePath()
                    + File.separator + "static"
                    + File.separator + "image");

    @Override
    public FileUploadResponse uploadFile(MultipartFile file,
                                         String uploaderName) throws IOException {
        if (!Files.exists(UPLOAD_PATH)) {
            Files.createDirectories(UPLOAD_PATH);
        }

        // file format validation
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
            throw new FileNotSupportedException("only .jpeg and .png images are " + "supported");
        }

        String timeStampedFileName = new SimpleDateFormat("ssmmHHddMMyyyy")
                .format(new Date()) + "_" + file.getOriginalFilename();

        Path filePath = UPLOAD_PATH.resolve(timeStampedFileName);
        Files.copy(file.getInputStream(), filePath);

        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/").path(timeStampedFileName).toUriString();

        String fileDownloadUri =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/file/download/").path(timeStampedFileName).toUriString();

        FileDetails fileDetails = FileDetails
                .builder()
                .fileName(file.getOriginalFilename())
                .fileUri(fileUri)
                .fileDownloadUri(fileDownloadUri)
                .fileSize(file.getSize())
                .uploaderName(uploaderName)
                .build();

        this.fileDetailsRepository.save(fileDetails);

        FileUploadResponse fileUploadResponse =
                new FileUploadResponse(fileDetails.getFileDetailId(),
                        file.getOriginalFilename(), fileUri, fileDownloadUri,
                        file.getSize(),
                        uploaderName);

        return fileUploadResponse;
    }

    @Override
    public Resource fetchFileAsResource(String fileName) throws FileNotFoundException {

        try {
            Path filePath = UPLOAD_PATH.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    @Override
    public List<FileDetails> getAllFiles() {
        return this.fileDetailsRepository.findAll();
    }

}
