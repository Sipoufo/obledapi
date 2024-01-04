package com.example.obledapi.services.commandService;

import com.example.obledapi.Entities.Command;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommandService {
    public ResponseEntity<?> createCommand(Command command);
    public ResponseEntity<?> getAllCommands(String token, Pageable pageable);
    public ResponseEntity<?> getOneCommand(String token, Long commandId);
}
