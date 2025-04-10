package com.focusbuddy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "❌ Username is mandatory")
    private String username;

    @Email(message = "❌ Invalid email format")
    @NotNull(message = "❌ Email is required")
    private String email;

    @NotBlank(message = "❌ Password is mandatory")
    private String password;
}
