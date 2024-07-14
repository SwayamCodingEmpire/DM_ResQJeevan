package com.dmrj.dm_resqjeevan.controllers;

import com.dmrj.dm_resqjeevan.dto.Login;
import com.dmrj.dm_resqjeevan.dto.LoginResponse;
import com.dmrj.dm_resqjeevan.entities.AdminInfo;
import com.dmrj.dm_resqjeevan.services.serviceImpl.AuthenticationServiceImpl;
import com.dmrj.dm_resqjeevan.services.serviceImpl.JwtServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final JwtServiceImpl jwtServiceImpl;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AuthenticationServiceImpl authenticationService;

    public PublicController(JwtServiceImpl jwtServiceImpl, AuthenticationServiceImpl authenticationService) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginSuperAdmin(@RequestBody Login login){
        UserDetails userDetails = authenticationService.authenticate(login);
        logger.info(userDetails.toString());
        if(userDetails instanceof AdminInfo){
            AdminInfo authenticatedAdmin = (AdminInfo)userDetails;
            String jwtToken = jwtServiceImpl.generateToken(authenticatedAdmin);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtServiceImpl.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        }
        else {
            throw new IllegalStateException("User is Invalid");
        }

    }
}
