package com.lufthansa.webapp.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import com.lufthansa.backend.model.AuthJWT;
import com.lufthansa.backend.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lufthansa.backend.service.LoginService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@ComponentScan(basePackages = "com.lufthansa.backend.*")
public class AuthenticationController {

    private final LoginService loginService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", responses = {
            @ApiResponse(description = "User logged in successfully", responseCode = "200"),
            @ApiResponse(description = "Username not found", responseCode = "422")
    })
    public AuthJWT login(@RequestBody AuthUser authUser) {
        return loginService.login(authUser.getUsername(),authUser.getPassword());
    }
}
