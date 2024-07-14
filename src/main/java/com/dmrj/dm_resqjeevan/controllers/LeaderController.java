package com.dmrj.dm_resqjeevan.controllers;

import com.dmrj.dm_resqjeevan.dto.TeamDto;
import com.dmrj.dm_resqjeevan.services.serviceImpl.LeaderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leader")
public class LeaderController {
    private final LeaderServiceImpl leaderService;
    private final Logger logger = LoggerFactory.getLogger(LeaderController.class);

    public LeaderController(LeaderServiceImpl leaderService) {
        this.leaderService = leaderService;
    }

    @PostMapping("/form-team")
    public ResponseEntity<Boolean> formteam(@RequestBody TeamDto teamDto) {
        logger.info(teamDto.toString());
        return ResponseEntity.ok(leaderService.formTeam(teamDto));
    }
}
