package com.dmrj.dm_resqjeevan.controllers;

import com.dmrj.dm_resqjeevan.dto.PersonnelIInfoData;
import com.dmrj.dm_resqjeevan.dto.PersonnelInfoDto;
import com.dmrj.dm_resqjeevan.entities.PersonnelInfo;
import com.dmrj.dm_resqjeevan.services.serviceImpl.RegistrarServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/registrar")
public class RegistrarController {
    private final Logger logger = LoggerFactory.getLogger(RegistrarController.class);
    private final RegistrarServiceImpl registrarService;

    public RegistrarController(RegistrarServiceImpl registrarService) {
        this.registrarService = registrarService;
    }
    @PostMapping("/register-personnel")
    public ResponseEntity<PersonnelInfoDto> registerPersonnel(@ModelAttribute PersonnelInfoDto personnelInfoDto) {
        logger.info(personnelInfoDto.toString());
        return ResponseEntity.ok(registrarService.registerPersonnel(personnelInfoDto));
    }

    @PutMapping("/reRegister")
    public ResponseEntity<PersonnelInfoDto> registerPersonnelAgain(@ModelAttribute PersonnelInfoDto personnelInfoDto) {
        logger.info(personnelInfoDto.toString());
        return ResponseEntity.ok(registrarService.reRegisterPersonnel(personnelInfoDto));
    }

    @GetMapping("/{regimentNo}")
    public PersonnelIInfoData getPersonnelInfo(@PathVariable("regimentNo") String regimentNo) {
        logger.info(regimentNo);
        return registrarService.getPersonnelInfo(regimentNo);
    }
    @GetMapping("/get-image/{regimentNo}")
    public ResponseEntity<byte[]> getPersonnelImage(@PathVariable("regimentNo") String regimentNo) {
        logger.info(regimentNo);
        return ResponseEntity.ok(registrarService.getPersonnelImage(regimentNo));
    }
}
