package com.doctorbookingapp.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {

    @NotBlank(message = "Username or Email cannot be blank")
    String username;

    @NotBlank(message = "Password cannot be blank")
    String password;
}


