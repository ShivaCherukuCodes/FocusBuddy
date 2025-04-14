package com.focusbuddy.service;

import com.focusbuddy.dto.LoginDTO;
import com.focusbuddy.dto.UserDTO;
import com.focusbuddy.model.User;
import com.focusbuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    public boolean registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return false;
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return false;
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        emailService.sendWelcomeEmail(user);

        return true;
    }


    public Optional<User> getAuthenticatedUser(LoginDTO loginDTO) {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(
                loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail());

        if (userOpt.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    public User getUserByUsernameOrEmail(String input) {
        return userRepository.findByUsernameOrEmail(input, input).orElse(null);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        Optional<User> optionalUser = getUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);

                // Send success email
                try {
                    emailService.sendPasswordChangeSuccessEmail(user.getEmail(), user.getUsername());
                } catch (Exception e) {
                    e.printStackTrace(); // Or log it properly
                }

                return true;
            }
        }
        return false;
    }


}
