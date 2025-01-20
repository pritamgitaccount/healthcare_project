package com.doctorbookingapp.config;

import com.doctorbookingapp.jwt.JwtAuthenticationFilter;
import com.doctorbookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    // private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    /*
        UserDetailsService:
   Purpose: Loads user-specific data during authentication.
   Implementation: Fetches the user from the UserRepository based on the username.
   If the user does not exist, it throws UsernameNotFoundException.
    */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            log.debug("Loading user by username: {}", username);
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        };
    }


    /*
BCryptPasswordEncoder:
Purpose: Encodes passwords using the BCrypt hashing algorithm for secure storage.
 */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("Creating BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    /*
       AuthenticationManager:
   Purpose: Handles authentication operations like validating credentials.
   Implementation: Obtained from the AuthenticationConfiguration bean.
    */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.debug("Creating AuthenticationManager bean");
        return config.getAuthenticationManager();
    }

    /*
   AuthenticationProvider:

   Purpose: Customizes the authentication process.
   Implementation: Uses DaoAuthenticationProvider to integrate the UserDetailsService and password encoder.
    */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.debug("Creating AuthenticationProvider bean");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /*
    SecurityFilterChain:

    Purpose: Configures the security rules for HTTP requests.
    Implementation: Defines request authorization, session policies, and JWT filter integration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Configuring security filter chain");

        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .cors(AbstractHttpConfigurer::disable)  // Disable CORS
                .authorizeHttpRequests(auth -> auth
                        // Allow unauthenticated access to Swagger, Actuator, and authentication endpoints
                        .requestMatchers("/auth/**",
                                "/api/doctors/search",
                                "/api/hospitals/hospital/search",
                                "/actuator/**", "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/index.html",
                                "/api/patients/users"
                        ).permitAll()
                        .requestMatchers("/api/doctors/update").hasRole("ADMIN")
                        .anyRequest().authenticated()  // All other requests need to be authenticated
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(STATELESS)  // Set session management to stateless
                )
                .authenticationProvider(authenticationProvider())  // Set custom authentication provider
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // Add JWT filter
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                                    response.setStatus(HttpStatus.OK.value());
                                    response.getWriter().write("Logged out successfully");
                                }
                        )
                );
        return http.build();
    }
}

