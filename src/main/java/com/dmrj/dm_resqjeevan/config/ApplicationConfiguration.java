package com.dmrj.dm_resqjeevan.config;

import com.cloudinary.Cloudinary;
import com.dmrj.dm_resqjeevan.helpers.AppConstants;
import com.dmrj.dm_resqjeevan.services.serviceImpl.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfiguration {
    private final CustomUserDetailService userDetailService;

    public ApplicationConfiguration(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();
        config.put("cloud_name", "dust1xmou");
        config.put("api_key", "996128832981947");
        config.put("api_secret", "k2Fti1dOL3-tI4Tsys3A5bSE1LA");
        config.put("secure", true);
        return new Cloudinary(config);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("SUPER_ADMIN").implies("CONTROL_ROOM")
                .role("CONTROL_ROOM").implies("REGISTRAR")
                .role("REGISTRAR").implies("LEADER")
                .role("LEADER").implies("PERSONNEL")
                .build();
    }
}
