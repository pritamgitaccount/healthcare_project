package com.doctorbookingapp.payload;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private Long id; // May not be needed for signup, unless you plan to handle updates

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    private String lastName; // Optional field

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Allow roles to be provided dynamically
    private Set<String> roles;
}
