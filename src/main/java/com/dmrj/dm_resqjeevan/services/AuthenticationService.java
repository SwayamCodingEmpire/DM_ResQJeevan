package com.dmrj.dm_resqjeevan.services;


import com.dmrj.dm_resqjeevan.dto.Login;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(Login login);
}
