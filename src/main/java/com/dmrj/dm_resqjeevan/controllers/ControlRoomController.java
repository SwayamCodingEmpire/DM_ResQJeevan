package com.dmrj.dm_resqjeevan.controllers;

import com.dmrj.dm_resqjeevan.dto.PersonnelInfoDto;
import com.dmrj.dm_resqjeevan.dto.PromoteOrDemote;
import com.dmrj.dm_resqjeevan.entities.PersonnelInfo;
import com.dmrj.dm_resqjeevan.services.ControlRoomService;
import com.dmrj.dm_resqjeevan.services.serviceImpl.ControlRoomServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/control-room")
public class ControlRoomController {
    private final ControlRoomServiceImpl controlRoomService;

    public ControlRoomController(ControlRoomServiceImpl controlRoomService) {
        this.controlRoomService = controlRoomService;
    }

    @PatchMapping("/promoteToLeader")
    public ResponseEntity<String> promoteDemote(PromoteOrDemote promoteOrDemote) {
        if(controlRoomService.promoteToLeader(promoteOrDemote)){
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.ok("Fail");
    }
    @GetMapping("/getAllPersonnel")
    public ResponseEntity<List<PersonnelInfoDto>> getAllPersonnel() {
        return ResponseEntity.ok(controlRoomService.getAllPersonnels());
    }
    @GetMapping("/getPersonnelByLocation/{location}")
    public ResponseEntity<List<PersonnelInfoDto>> getPersonnelByLocation(@PathVariable("location") String location) {
        return ResponseEntity.ok(controlRoomService.getAllPersonnelsByLocation(location));
    }
    @GetMapping("/getPersonnelByRole/{role}")
    public ResponseEntity<List<PersonnelInfoDto>> getPersonnelByRole(@PathVariable("role") String role) {
        return ResponseEntity.ok(controlRoomService.getAllPersonnelsByRole(role));
    }
}