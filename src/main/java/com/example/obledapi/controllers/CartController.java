package com.example.obledapi.controllers;

import com.example.obledapi.Entities.FileDetails;
import com.example.obledapi.exceptions.FileNotSupportedException;
import com.example.obledapi.payloads.requests.UploadFileRequest;
import com.example.obledapi.payloads.responses.FileUploadResponse;
import com.example.obledapi.payloads.responses.MessageResponse;
import com.example.obledapi.services.cartService.CartService;
import com.example.obledapi.services.cartService.CartServiceImpl;
import com.example.obledapi.services.fileUploadService.FileUploadService;
import com.example.obledapi.services.fileUploadService.FileUploadServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    /*@Autowired
    private FileUploadService fileUploadService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<FileDetails> getAllFiles() {
        return this.fileUploadService.getAllFiles();
    }

    @PostMapping
    public ResponseEntity<?> uploadFiles(@RequestBody UploadFileRequest uploadFileRequest) {
        System.out.println("uploadFileRequest.getFile()");
        System.out.println(uploadFileRequest.getFile());
        try {
            fileUploadService.uploadFile(uploadFileRequest.getFile(), uploadFileRequest.getName());
            return ResponseEntity
                    .ok(new MessageResponse("Cart added !"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName,
                                               HttpServletRequest request) {

        try {
            Resource resource = this.fileUploadService.fetchFileAsResource(fileName);
            String contentType =
                    request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }*/
}
