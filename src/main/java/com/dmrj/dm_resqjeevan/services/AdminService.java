package com.dmrj.dm_resqjeevan.services;

import com.dmrj.dm_resqjeevan.dto.AdminInfoDto;
import com.dmrj.dm_resqjeevan.entities.AdminInfo;

public interface AdminService {
    AdminInfoDto saveAdmin(AdminInfoDto adminInfoDto, String role);
}
