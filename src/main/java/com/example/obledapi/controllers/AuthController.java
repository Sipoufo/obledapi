package com.example.obledapi.controllers;

import com.example.obledapi.Entities.RefreshToken;
import com.example.obledapi.Entities.Users;
import com.example.obledapi.exceptions.TokenRefreshException;
import com.example.obledapi.payloads.requests.LoginRequest;
import com.example.obledapi.payloads.requests.RefreshTokenRequest;
import com.example.obledapi.payloads.requests.SignUpRequest;
import com.example.obledapi.payloads.requests.TokenRefreshRequest;
import com.example.obledapi.payloads.responses.JwtAuthenticationResponse;
import com.example.obledapi.payloads.responses.MessageResponse;
import com.example.obledapi.payloads.responses.TokenVerifyResponse;
import com.example.obledapi.payloads.responses.UserMachineDetails;
import com.example.obledapi.security.jwt.JwtUtils;
import com.example.obledapi.services.refreshTokenService.RefreshTokenService;
import com.example.obledapi.services.refreshTokenService.RefreshTokenServiceImpl;
import com.example.obledapi.services.userService.UserService;
import com.example.obledapi.services.userService.UserServiceImpl;
import com.example.obledapi.utils.HttpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private final UserService userService = new UserServiceImpl();
    @Autowired
    private final RefreshTokenService refreshTokenService = new RefreshTokenServiceImpl();

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String ipAddress = HttpUtils.getClientIp();

        UserMachineDetails userMachineDetails = UserMachineDetails
                .builder()
                .ipAddress(ipAddress)
                .browser(userAgent.getBrowser().getName())
                .operatingSystem(userAgent.getOperatingSystem().getName())
                .build();
        System.out.println("Je passe");

        Optional<Users> user = userService.getUserByEmail(loginRequest.getUsername());
        System.out.println("Je passe");

        if (user.isEmpty() || !encoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email or password error"));
        }
        System.out.println("Je passe");

        String jwt = jwtUtils.generateJwtToken(user.get().getEmail());
        System.out.println(jwt);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.get().getEmail(), userMachineDetails);

        System.out.println(refreshToken.getToken());
        return ResponseEntity.ok(JwtAuthenticationResponse.builder().token(jwt).user(user.get()).refreshToken(refreshToken.getToken()).firstName(user.get().getFirstName()).build());
    }

    @PostMapping("/verifyAccessToken")
    public ResponseEntity<?> VerifyAccessToken(@RequestHeader (name="Authorization") String token) {
        System.out.println(token.substring(7));
        boolean isValidated = jwtUtils.validateJwtToken(token.substring(7));
        System.out.println(isValidated);
        if (isValidated) {
            return ResponseEntity
                    .ok()
                    .body(
                            TokenVerifyResponse
                                    .builder()
                                    .isValid(true)
                                    .message("Is Valid")
                                    .build()
                    );
        }
        return ResponseEntity
                .ok()
                .body(
                        TokenVerifyResponse
                                .builder()
                                .isValid(false)
                                .message("Is expired")
                                .build()
                );
    }

    @PostMapping("/verifyRefreshToken")
    public ResponseEntity<?> VerifyRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken());
        if (refreshToken.isEmpty() || refreshTokenService.verifyExpiration(refreshToken.get()) == null) {
            return ResponseEntity
                    .ok()
                    .body(
                            TokenVerifyResponse
                                    .builder()
                                    .isValid(false)
                                    .message("RefreshToken not found")
                                    .build()
                    );
        }

        return ResponseEntity
                .ok()
                .body(
                        TokenVerifyResponse
                                .builder()
                                .isValid(true)
                                .message("Is not expired")
                                .build()
                );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        JwtAuthenticationResponse jwtAuthenticationResponse = refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map((user) -> {
                    String token = jwtUtils.generateJwtToken(user.getEmail());
                    return JwtAuthenticationResponse.builder().token(token).user(user).refreshToken(requestRefreshToken).build();
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@Validated @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String ipAddress = HttpUtils.getClientIp();

        UserMachineDetails userMachineDetails = UserMachineDetails
                .builder()
                .ipAddress(ipAddress)
                .browser(userAgent.getBrowser().getName())
                .operatingSystem(userAgent.getOperatingSystem().getName())
                .build();

        if (userService.isEmailExisted(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already in use!"));
        }
        System.out.println("je passe 2");

        if (userService.isPhoneExisted(signUpRequest.getPhone())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Phone is already in use!"));
        }
        System.out.println(signUpRequest.getPassword());

        // Create new user's account
        String password = "Azerty@12";
        Users user = Users
                .builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .phone(signUpRequest.getPhone())
                .password(encoder.encode(password))
                .build();
        userService.createUser(user);

        return ResponseEntity.ok(MessageResponse.builder().message("User created !").build());
    }

}
