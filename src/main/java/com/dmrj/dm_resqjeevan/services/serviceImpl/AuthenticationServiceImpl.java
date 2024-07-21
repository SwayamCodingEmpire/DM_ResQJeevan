package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.Login;
import com.dmrj.dm_resqjeevan.repositories.AdminRepo;
import com.dmrj.dm_resqjeevan.repositories.PersonnelRepo;
import com.dmrj.dm_resqjeevan.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationProvider authenticationProvider;
    private final PersonnelRepo personnelRepo;
    private final AdminRepo adminRepo;

    public AuthenticationServiceImpl(AuthenticationProvider authenticationProvider, PersonnelRepo personnelRepo, AdminRepo adminRepo) {
        this.authenticationProvider = authenticationProvider;
        this.personnelRepo = personnelRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails authenticate(Login login) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        try {
            return adminRepo.findById(login.getUsername()).orElseThrow();
        } catch (Exception e) {
            return personnelRepo.findById(login.getUsername()).orElseThrow();

        }
    }
}
