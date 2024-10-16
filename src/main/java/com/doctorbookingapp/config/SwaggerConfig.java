package com.doctorbookingapp.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Doctor Booking App API",
                description = "API for managing doctor bookings, profiles, and appointments",
                summary = "This API provides functionalities for managing doctors, patients, and bookings in a healthcare system.",
//                termsOfService = "https://example.com/terms",  // Update with real terms if needed
                contact = @Contact(
                        name = "Doctor Booking Support",
                        email = "pritamckb99@gmail.com",
                        url = "https://github.com/pritamgitaccount"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"  // Update with the correct license URL
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "Bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}