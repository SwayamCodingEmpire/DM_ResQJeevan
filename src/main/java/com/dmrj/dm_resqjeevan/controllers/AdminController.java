package com.dmrj.dm_resqjeevan.controllers;

import com.dmrj.dm_resqjeevan.dto.AdminInfoDto;
import com.dmrj.dm_resqjeevan.entities.AdminInfo;
import com.dmrj.dm_resqjeevan.helpers.AppConstants;
import com.dmrj.dm_resqjeevan.services.serviceImpl.AuthenticationServiceImpl;
import com.dmrj.dm_resqjeevan.services.serviceImpl.JwtServiceImpl;
import com.dmrj.dm_resqjeevan.services.serviceImpl.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super-admin")
public class AdminController {
    private final JwtServiceImpl jwtServiceImpl;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminServiceImpl adminService;
    private final AuthenticationServiceImpl authenticationService;

    public AdminController(AdminServiceImpl adminService, AuthenticationServiceImpl authenticationService, JwtServiceImpl jwtServiceImpl) {
        this.adminService = adminService;
        this.authenticationService = authenticationService;
        this.jwtServiceImpl = jwtServiceImpl;
    }
    @PostMapping("/register")
    public ResponseEntity<AdminInfoDto> registerSuperAdmin(@RequestBody AdminInfoDto adminInfoDto){
        logger.info(adminInfoDto.toString());
        return ResponseEntity.ok(adminService.saveAdmin(adminInfoDto, AppConstants.ROLE_SUPER_ADMIN));
    }
    @PostMapping("/registrar-register")
    public ResponseEntity<AdminInfoDto> registerRegistrar(@RequestBody AdminInfoDto adminInfoDto){
        logger.info(adminInfoDto.toString());
        return ResponseEntity.ok(adminService.saveAdmin(adminInfoDto, AppConstants.ROLE_REGISTRAR));
    }
    @PostMapping("/controlRoom-register")
    public ResponseEntity<AdminInfoDto> registerControlRoom(@RequestBody AdminInfoDto adminInfoDto){
        logger.info(adminInfoDto.toString());
        return ResponseEntity.ok(adminService.saveAdmin(adminInfoDto, AppConstants.ROLE_CONTROL_ROOM));
    }


}
