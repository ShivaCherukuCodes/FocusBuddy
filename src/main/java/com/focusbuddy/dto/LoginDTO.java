package com.focusbuddy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
    @NotBlank(message = "Username/Email is required")
    private String usernameOrEmail;
    @NotBlank(message = "Password is required")
    private String password;

}
