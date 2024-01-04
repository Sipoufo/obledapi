package com.example.obledapi.controllers;

import com.example.obledapi.services.userService.UserService;
import com.example.obledapi.services.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private final UserService userService = new UserServiceImpl();

//    @GetMapping("")
//    public ResponseEntity<?> getOneUserByToken(@RequestHeader(name="Authorization") String token) {
//        return userService.getOneUserByToken(token);
//    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getAllUsers(@RequestHeader(name="Authorization") String token, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        return userService.getAllUsers(pageable, token);
    }
//    @PutMapping("")
//    public ResponseEntity<?> updateOneUserByToken(@RequestBody UserSetting userSetting, @RequestHeader (name="Authorization") String token) {
//        return userService.updateSettingUser(userSetting, token);
//    }

//    @GetMapping("/{pageNumber}/{pageSize}")
//    public ResponseEntity<?> getAllUsers_admin(@RequestHeader (name="Authorization") String token, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
//        System.out.println("Je passe 2");
//        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
//        return userService.getAllUsers(pageable, token);
//    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> getUsersInfo_admin(@RequestHeader (name="Authorization") String token) {
        return userService.getUsersInfo_admin(token);
    }

    @GetMapping("/adminInfo")
    public ResponseEntity<?> getAdminsInfo_admin(@RequestHeader (name="Authorization") String token) {
        return userService.getAdminsInfo_admin(token);
    }

    @GetMapping("/userInfo/{userId}")
    public ResponseEntity<?> getUsersInfo_admin(@PathVariable("userId") long userId) {
        return userService.getOneUser(userId);
    }

    @PutMapping("/block/{userId}/{block}")
    public ResponseEntity<?> blockUser(@RequestHeader (name="Authorization") String token, @PathVariable("userId") long userId, @PathVariable("block") boolean block) {
        return userService.blockUser(token, userId, block);
    }

    @GetMapping("/oneUser/{userId}")
    public ResponseEntity<?> blockUser(@RequestHeader (name="Authorization") String token, @PathVariable("userId") long userId) {
        return userService.findUserByUserId(token, userId);
    }
}
