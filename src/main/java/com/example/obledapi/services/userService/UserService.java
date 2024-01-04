package com.example.obledapi.services.userService;

import com.example.obledapi.Entities.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    public HttpStatus createUser(Users user);
    public ResponseEntity<?> getAllUsers(Pageable pageable, String token);
    public ResponseEntity<?> getUsersInfo_admin(String token);
    public ResponseEntity<?> getAdminsInfo_admin(String token);
    public ResponseEntity<?> getOneUser(long userId);
    public ResponseEntity<?> getOneUserByToken(String token);
    public HttpStatus updateSUser(Users user, long userId);
    public HttpStatus deleteUser(long userId);
    public boolean isPhoneExisted(String phone);
    public Optional<Users> getUserByToken(String token);
    public Optional<Users> getUserByEmail(String email);
    public ResponseEntity<?> resetPassword(String token, String newPassword, String confirmPassword);
    public ResponseEntity<?> findUserByUserId(String token, long userId);
    public ResponseEntity<?> blockUser(String token, long userId, boolean isBlock);
    public boolean isEmailExisted(String email);

}
