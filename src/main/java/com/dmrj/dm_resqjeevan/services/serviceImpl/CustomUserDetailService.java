package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.entities.AdminInfo;
import com.dmrj.dm_resqjeevan.repositories.AdminRepo;
import com.dmrj.dm_resqjeevan.repositories.PersonnelRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final AdminRepo adminRepo;
    private final PersonnelRepo personnelRepo;
    Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    public CustomUserDetailService(AdminRepo adminRepo, PersonnelRepo personnelRepo) {
        this.adminRepo = adminRepo;
        this.personnelRepo = personnelRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminInfo adminInfo = adminRepo.findById(username).orElse(null);
        if(adminInfo == null) {
            return personnelRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not with Username : " + username));
        }
        return adminInfo;
    }
}
