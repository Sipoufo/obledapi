package com.example.obledapi.services.commandService;

import com.example.obledapi.Entities.Command;
import com.example.obledapi.Entities.Users;
import com.example.obledapi.payloads.responses.DataResponse;
import com.example.obledapi.payloads.responses.MessageResponse;
import com.example.obledapi.repositories.CommandRepository;
import com.example.obledapi.services.userService.UserService;
import com.example.obledapi.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandServiceImpl implements CommandService{
    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private UserService userService = new UserServiceImpl();

    @Override
    public ResponseEntity<?> createCommand(Command command) {
        commandRepository.save(command);
        return ResponseEntity.ok(new MessageResponse("Cart created !"));
    }

    @Override
    public ResponseEntity<?> getAllCommands(String token, Pageable pageable) {
        return ResponseEntity.ok(
                DataResponse
                        .builder()
                        .data(commandRepository.findAll(pageable).getContent())
                        .dataNumber(commandRepository.findAll(pageable).getContent().size())
                        .actualPage(pageable.getPageNumber() + 1)
                        .pageable(pageable)
                        .build()
        );
        // return ResponseEntity.ok(DataResponse.builder().data((List<?>) commandRepository.findAll(pageable)).build());
    }

    @Override
    public ResponseEntity<?> getOneCommand(String token, Long commandId) {
        Optional<Users> organizer = userService.getUserByToken(token);
        if (organizer.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        return ResponseEntity.ok(commandRepository.findById(commandId));
    }
}
