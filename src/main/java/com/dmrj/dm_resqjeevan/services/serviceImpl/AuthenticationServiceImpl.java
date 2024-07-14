package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.Login;
import com.dmrj.dm_resqjeevan.repositories.AdminRepo;
import com.dmrj.dm_resqjeevan.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationProvider authenticationProvider;
    private final AdminRepo adminRepo;

    public AuthenticationServiceImpl(AuthenticationProvider authenticationProvider, AdminRepo adminRepo) {
        this.authenticationProvider = authenticationProvider;
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails authenticate(Login login) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        return adminRepo.findById(login.getUsername()).orElseThrow();
    }
}
