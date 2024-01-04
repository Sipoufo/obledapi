package com.example.obledapi.repositories;

import com.example.obledapi.Entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {
}
