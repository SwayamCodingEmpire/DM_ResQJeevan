package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.PersonnelInfoDto;
import com.dmrj.dm_resqjeevan.dto.PromoteOrDemote;
import com.dmrj.dm_resqjeevan.entities.PersonnelInfo;
import com.dmrj.dm_resqjeevan.helpers.AppConstants;
import com.dmrj.dm_resqjeevan.repositories.PersonnelRepo;
import com.dmrj.dm_resqjeevan.services.ControlRoomService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ControlRoomServiceImpl implements ControlRoomService {
    private final PersonnelRepo personnelRepo;
    private final ModelMapper modelMapper;

    public ControlRoomServiceImpl(PersonnelRepo personnelRepo, ModelMapper modelMapper) {
        this.personnelRepo = personnelRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean promoteToLeader(PromoteOrDemote promoteOrDemote) {
        if(personnelRepo.existsById(promoteOrDemote.getRegimentNo())){
            return (personnelRepo.updateRole(promoteOrDemote.getRegimentNo(), AppConstants.ROLE_LEADER) == 1);
        }
        throw new UsernameNotFoundException("No such user exists with Username : " + promoteOrDemote.getRegimentNo());
    }

    @Override
    public List<PersonnelInfoDto> getAllPersonnels() {
        List<PersonnelInfo> personnelInfos = personnelRepo.findAll();
        List<PersonnelInfoDto> personnelInfoDtos = new ArrayList<>();
        for (PersonnelInfo personnelInfo : personnelInfos) {
            personnelInfoDtos.add(convertToPersonnelInfoDto(personnelInfo));
        }
        return personnelInfoDtos;
    }

    @Override
    public List<PersonnelInfoDto> getAllPersonnelsByLocation(String location) {
        List<PersonnelInfo> personnelInfos = personnelRepo.findAllByLocation(location);
        List<PersonnelInfoDto> personnelInfoDtos = new ArrayList<>();
        for (PersonnelInfo personnelInfo : personnelInfos) {
            personnelInfoDtos.add(convertToPersonnelInfoDto(personnelInfo));
        }
        return personnelInfoDtos;
    }

    @Override
    public List<PersonnelInfoDto> getAllPersonnelsByRole(String role) {
        List<PersonnelInfo> personnelInfos = personnelRepo.findAllByLocation(role);
        List<PersonnelInfoDto> personnelInfoDtos = new ArrayList<>();
        for (PersonnelInfo personnelInfo : personnelInfos) {
            personnelInfoDtos.add(convertToPersonnelInfoDto(personnelInfo));
        }
        return personnelInfoDtos;
    }

    private PersonnelInfo convertToPersonnelInfo(PersonnelInfoDto personnelInfoDto){
        return modelMapper.map(personnelInfoDto, PersonnelInfo.class);
    }

    private PersonnelInfoDto convertToPersonnelInfoDto(PersonnelInfo personnelInfo){
        return modelMapper.map(personnelInfo, PersonnelInfoDto.class);
    }
}
