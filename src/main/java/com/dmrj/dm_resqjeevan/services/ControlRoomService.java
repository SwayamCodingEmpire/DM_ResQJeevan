package com.dmrj.dm_resqjeevan.services;

import com.dmrj.dm_resqjeevan.dto.PersonnelInfoDto;
import com.dmrj.dm_resqjeevan.dto.PromoteOrDemote;
import com.dmrj.dm_resqjeevan.entities.PersonnelInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ControlRoomService {
    Boolean promoteToLeader(PromoteOrDemote promoteOrDemote);

    List<PersonnelInfoDto> getAllPersonnels();
    List<PersonnelInfoDto> getAllPersonnelsByLocation(String location);
    List<PersonnelInfoDto> getAllPersonnelsByRole(String role);
}
