package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = this.userRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with username: " + username)
//        );
//        logger.error("User not found: {}", username);
//
//        return user;
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        logger.info("User found: {}", username);
        return user;
    }

}
