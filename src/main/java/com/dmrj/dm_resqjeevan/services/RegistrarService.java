package com.dmrj.dm_resqjeevan.services;

import com.dmrj.dm_resqjeevan.dto.PersonnelIInfoData;
import com.dmrj.dm_resqjeevan.dto.PersonnelInfoDto;
import com.dmrj.dm_resqjeevan.entities.PersonnelInfo;

public interface RegistrarService {
    PersonnelInfoDto registerPersonnel(PersonnelInfoDto personnelInfoDto);
    PersonnelInfoDto reRegisterPersonnel(PersonnelInfoDto personnelInfoDto);
    PersonnelIInfoData getPersonnelInfo(String regimentNo);
    byte[] getPersonnelImage(String regimentNo);
}
