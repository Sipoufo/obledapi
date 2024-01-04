package com.example.obledapi.repositories;

import com.example.obledapi.Entities.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailRepository extends JpaRepository<FileDetails, Long> {
}
