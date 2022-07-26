package com.lufthansa.backend.service;

import com.lufthansa.backend.exception.CustomException;
import com.lufthansa.backend.model.AuthJWT;
import com.lufthansa.backend.repository.UserRepository;
import com.lufthansa.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@ComponentScan(basePackages = "com.lufthansa.backend.*")
public class LoginService {

    private static final Logger logger = LogManager.getLogger(LoginService.class);
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthJWT login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            AuthJWT token = new AuthJWT();
            token.setToken(jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles()));
            token.setUsername(username);
            token.setAuthorities(userRepository.findByUsername(username).getRoles());
            logger.info("Returning JWT Token");
            return token;

        } catch (AuthenticationException e) {
            logger.warn("The provided username or password is invalid.");
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
