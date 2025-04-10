package com.focusbuddy.controller;

import com.focusbuddy.config.JwtUtil;
import com.focusbuddy.dto.LoginDTO;
import com.focusbuddy.dto.LoginResponseDTO;
import com.focusbuddy.dto.UserDTO;
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
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
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



}
