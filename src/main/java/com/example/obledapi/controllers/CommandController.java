package com.example.obledapi.controllers;

import com.example.obledapi.Entities.Command;
import com.example.obledapi.payloads.requests.CommandRequest;
import com.example.obledapi.services.commandService.CommandService;
import com.example.obledapi.services.commandService.CommandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/command")
public class CommandController {
    @Autowired
    private CommandService commandService = new CommandServiceImpl();

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getAllUsers(@RequestHeader(name="Authorization") String token, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        System.out.println("Pageable pageable = PageRequest.of(pageNumber-1, pageSize);");
        System.out.println(pageNumber);
        System.out.println(pageSize);
        return commandService.getAllCommands(token, pageable);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestHeader(name="Authorization") String token, @RequestBody CommandRequest commandRequest) {
        Command command = Command
                .builder()
                .userFirstName(commandRequest.getUserFirstName())
                .userLastName(commandRequest.getUserLastName())
                .ville(commandRequest.getVille())
                .address(commandRequest.getAddress())
                .userEmail(commandRequest.getUserEmail())
                .address(commandRequest.getAddress())
                .postCode(commandRequest.getPostCode())
                .phone(commandRequest.getPhone())
                .build();
        return commandService.createCommand(command);
    }
}
