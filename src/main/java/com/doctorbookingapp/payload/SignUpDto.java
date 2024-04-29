package com.doctorbookingapp.payload;

import jakarta.validation.constraints.*;
import lombok.*;



@Getter
@Setter
public class SignUpDto {

//    @NotBlank(message = "Name cannot be blank")
    private String name;

   // @NotBlank(message = "Username cannot be blank")
    private String username;

//    @NotBlank(message = "Email cannot be blank")
//    @Email(message = "Invalid email format")
    private String email;

//    @NotBlank(message = "Password cannot be blank")
//    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
