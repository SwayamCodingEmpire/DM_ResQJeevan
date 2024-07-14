package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.AdminInfoDto;
import com.dmrj.dm_resqjeevan.entities.AdminInfo;
import com.dmrj.dm_resqjeevan.excpetions.UserAlreadyExistsException;
import com.dmrj.dm_resqjeevan.repositories.AdminRepo;
import com.dmrj.dm_resqjeevan.services.AdminService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(AdminRepo adminRepo, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public AdminInfoDto saveAdmin(AdminInfoDto adminInfoDto, String role) {
        AdminInfo adminInfo = convertToAdminInfo(adminInfoDto);
        if(adminRepo.existsById(adminInfo.getUsername())){
            throw new UserAlreadyExistsException("The User Already Exists : "+adminInfo.getUsername());
        }
        adminInfo.setPassword(passwordEncoder.encode(adminInfo.getPassword()));
        adminInfo.setRole(role);
        return convertToAdminInfoDto(adminRepo.save(adminInfo));
    }

    private AdminInfo convertToAdminInfo(AdminInfoDto adminInfoDto) {
        return modelMapper.map(adminInfoDto, AdminInfo.class);
    }

    private AdminInfoDto convertToAdminInfoDto(AdminInfo adminInfo) {
        return modelMapper.map(adminInfo, AdminInfoDto.class);
    }
}
