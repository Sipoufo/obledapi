package com.example.obledapi.services.userService;

import com.example.obledapi.Entities.Users;
import com.example.obledapi.payloads.responses.DataResponse;
import com.example.obledapi.payloads.responses.MessageResponse;
import com.example.obledapi.repositories.UserRepository;
import com.example.obledapi.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public HttpStatus createUser(Users user) {
        userRepository.save(user);
        return HttpStatus.CREATED;
    }

    @Override
    public ResponseEntity<?> getAllUsers(Pageable pageable, String token) {
        return ResponseEntity.ok(DataResponse
                .builder()
                .data(userRepository.findAll(pageable).getContent())
                .dataNumber(userRepository.findAll(pageable).getContent().size())
                .actualPage(pageable.getPageNumber() + 1)
                .pageable(pageable)
                .build());
    }

    @Override
    public ResponseEntity<?> getUsersInfo_admin(String token) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAdminsInfo_admin(String token) {
        return null;
    }

    @Override
    public ResponseEntity<?> getOneUser(long userId) {
        return ResponseEntity.ok(userRepository.findById(userId));
    }

    @Override
    public ResponseEntity<?> getOneUserByToken(String token) {
        Optional<Users> organizer = getUserByToken(token);
        if (organizer.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        return ResponseEntity.ok(userRepository.findById(organizer.get().getUserId()));
    }

    @Override
    public HttpStatus updateSUser(Users user, long userId) {
        Optional<Users> user1 = userRepository.findById(userId);

        if (user1.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }

        user1.get().setFirstName(user.getFirstName());
        user1.get().setLastName(user.getLastName());
        user1.get().setPhone(user.getPhone());
        userRepository.save(user1.get());

        return HttpStatus.OK;
    }

    @Override
    public HttpStatus deleteUser(long userId) {
        Optional<Users> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }

        userRepository.deleteById(userId);
        return HttpStatus.OK;
    }


    @Override
    public boolean isPhoneExisted(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Optional<Users> getUserByToken(String token) {
        System.out.println("token => "+token);
        if (token.length() < 7) {
            return Optional.empty();
        }
        if (!jwtUtils.validateJwtToken(token.substring(7))) {
            return Optional.empty();
        }
        String email = jwtUtils.getEmailFromJwtToken(token.substring(7));
        System.out.println("email => "+email);
        return getUserByEmail(email);
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<?> resetPassword(String token, String newPassword, String confirmPassword) {
        return null;
    }


    public Users getOneUserL(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public ResponseEntity<?> findUserByUserId(String token, long userId) {
        Optional<Users> user1 = getUserByToken(token);
        if (user1.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not administrator !"));
        }
        Optional<Users> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        return ResponseEntity.ok(user.get());
    }

    @Override
    public ResponseEntity<?> blockUser(String token, long userId, boolean isBlock){
        Optional<Users> user1 = getUserByToken(token);
        if (user1.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You are not authenticate !"));
        }
        Optional<Users> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User don't found !"));
        }

        user.get().setBlock(isBlock);
        userRepository.save(user.get());

        return ResponseEntity.ok(MessageResponse
                .builder()
                .message(isBlock ? "User block" : "User unblock")
                .build());
    }

    @Override
    public boolean isEmailExisted(String email) {
        return userRepository.existsByEmail(email);
    }
}
