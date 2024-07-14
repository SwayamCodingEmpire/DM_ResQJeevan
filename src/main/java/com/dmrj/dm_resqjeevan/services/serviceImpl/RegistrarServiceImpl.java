package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.PersonnelIInfoData;
import com.dmrj.dm_resqjeevan.dto.PersonnelInfoDto;
import com.dmrj.dm_resqjeevan.entities.PersonnelInfo;
import com.dmrj.dm_resqjeevan.excpetions.UserAlreadyExistsException;
import com.dmrj.dm_resqjeevan.helpers.AppConstants;
import com.dmrj.dm_resqjeevan.helpers.MultiPartFileConverter;
import com.dmrj.dm_resqjeevan.repositories.PersonnelRepo;
import com.dmrj.dm_resqjeevan.services.RegistrarService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class RegistrarServiceImpl implements RegistrarService {
    private final Logger logger = LoggerFactory.getLogger(RegistrarServiceImpl.class);
    private final ModelMapper modelMapper;
    private final PersonnelRepo personnelRepo;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryImageServiceImpl cloudinaryImageService;

    public RegistrarServiceImpl(ModelMapper modelMapper, PersonnelRepo personnelRepo, PasswordEncoder passwordEncoder, CloudinaryImageServiceImpl cloudinaryImageService) {
        this.modelMapper = modelMapper;
        this.personnelRepo = personnelRepo;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryImageService = cloudinaryImageService;
    }

    @Override
    public PersonnelInfoDto registerPersonnel(PersonnelInfoDto personnelInfoDto) {
        PersonnelInfo personnelInfo = convertToPersonnelInfo(personnelInfoDto);
        if(personnelRepo.existsById(personnelInfo.getRegimentNo())){
            throw new UserAlreadyExistsException("The Personnel already exists");
        }
        personnelInfo.setPassword(passwordEncoder.encode(personnelInfo.getPassword()));
        personnelInfo.setUsername(personnelInfo.getRegimentNo());
        personnelInfo.setRole(AppConstants.ROLE_PERSONNEL);
        Map imageData = cloudinaryImageService.upload(personnelInfoDto.getImage());
        personnelInfo.setImage_public_id(imageData.get("public_id").toString());
        return convertToPersonnelInfoDto(personnelRepo.save(personnelInfo));
    }

    @Override
    public PersonnelInfoDto reRegisterPersonnel(PersonnelInfoDto personnelInfoDto) {
        PersonnelInfo personnelInfo = convertToPersonnelInfo(personnelInfoDto);
        personnelInfo.setPassword(passwordEncoder.encode(personnelInfo.getPassword()));
        personnelInfo.setUsername(personnelInfo.getRegimentNo());
        personnelInfo.setRole(AppConstants.ROLE_PERSONNEL);
        Map imageData = cloudinaryImageService.upload(personnelInfoDto.getImage());
        personnelInfo.setImage_public_id(imageData.get("public_id").toString());
        return convertToPersonnelInfoDto(personnelRepo.save(personnelInfo));
    }

    @Override
    public PersonnelIInfoData getPersonnelInfo(String regimentNo){
        PersonnelInfo personnelInfo = personnelRepo.findById(regimentNo).orElseThrow(()->new UsernameNotFoundException("No such user exists with Regiment No : " + regimentNo));
        return convertToPersonnelInfoData(personnelInfo);
    }

    public byte[] getPersonnelImage(String regimentNo){
        PersonnelInfo personnelInfo = personnelRepo.findById(regimentNo).orElseThrow(()->new UsernameNotFoundException("No such user exists with Regiment No : " + regimentNo));
        try {
            return StreamUtils.copyToByteArray(cloudinaryImageService.getImageFile(personnelInfo.getImage_public_id()));
        }catch (Exception e){
            throw new RuntimeException("Unable To get Image");
        }
    }

    private PersonnelInfo convertToPersonnelInfo(PersonnelInfoDto personnelInfoDto){
        return modelMapper.map(personnelInfoDto, PersonnelInfo.class);
    }

    private PersonnelInfoDto convertToPersonnelInfoDto(PersonnelInfo personnelInfo){
        return modelMapper.map(personnelInfo, PersonnelInfoDto.class);
    }

    private PersonnelIInfoData convertToPersonnelInfoData(PersonnelInfo personnelInfo){
        return modelMapper.map(personnelInfo, PersonnelIInfoData.class);
    }
}
