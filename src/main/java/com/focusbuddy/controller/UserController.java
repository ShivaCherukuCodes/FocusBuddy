package com.focusbuddy.controller;

import com.focusbuddy.config.JwtUtil;
import com.focusbuddy.dto.*;
import com.focusbuddy.model.User;
import com.focusbuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    // Register API
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) throws Exception {
        if (userDTO.getUsername() != null && !userDTO.getUsername().trim().isEmpty()) {
            boolean isCreated = userService.registerUser(userDTO);
            if (isCreated) {
                return ResponseEntity.ok("✅ User registered successfully!");
            } else {
                return ResponseEntity.badRequest().body("❌ Username or Email already exists.");
            }
        } else {
            return ResponseEntity.badRequest().body("❌ Username is mandatory for Registration");
        }
    }


    // Login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<User> userOpt = userService.getAuthenticatedUser(loginDTO);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Invalid credentials.");
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getUsername()); // or use email if preferred

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);

            Optional<User> userOpt = userService.getUserByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                UserProfileDTO profileDTO = new UserProfileDTO(
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt().toString()
                );

                return ResponseEntity.ok(profileDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Invalid or expired token.");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO dto, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);

        boolean isChanged = userService.changePassword(username, dto.getCurrentPassword(), dto.getNewPassword());

        if (isChanged) {
            return ResponseEntity.ok("✅ Password changed successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Current password is incorrect.");
        }
    }



}
