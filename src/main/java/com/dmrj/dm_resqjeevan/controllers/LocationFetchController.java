package com.dmrj.dm_resqjeevan.controllers;

import com.dmrj.dm_resqjeevan.dto.LocationDto;
import com.dmrj.dm_resqjeevan.dto.ChatNotification;
import com.dmrj.dm_resqjeevan.entities.Location;
import com.dmrj.dm_resqjeevan.services.serviceImpl.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class LocationFetchController {
    private final Logger logger = LoggerFactory.getLogger(LocationFetchController.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LocationService locationService;

    public LocationFetchController(SimpMessagingTemplate simpMessagingTemplate, LocationService locationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.locationService = locationService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload LocationDto locationDto, Principal principal){
        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                logger.info("Authenticated user: " + userDetails.getUsername());
            } else {
                logger.info("Could not retrieve Authentication object");
            }
        } else {
            logger.info("Principal is not an instance of Authentication");
        }
        Location savedMsg = locationService.save(locationDto,principal);
        simpMessagingTemplate.convertAndSendToUser(locationDto.getRecipientId(),"/queue/messages",new ChatNotification(
                savedMsg.getId(),
                savedMsg.getSenderId(),
                savedMsg.getRecipientId(),
                savedMsg.getLat(),
                savedMsg.getLng()
        ));
    }

}
