package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.LocationDto;
import com.dmrj.dm_resqjeevan.entities.Location;
import com.dmrj.dm_resqjeevan.excpetions.WebSocketRelatedException;
import com.dmrj.dm_resqjeevan.repositories.LocationRepo;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    private final LocationRepo locationRepo;
    private final ChatRoomServiceImpl chatRoomService;
    private final ModelMapper modelMapper;

    public LocationService(LocationRepo locationRepo, ChatRoomServiceImpl chatRoomService, ModelMapper modelMapper) {
        this.locationRepo = locationRepo;
        this.chatRoomService = chatRoomService;
        this.modelMapper = modelMapper;
    }

    public Location save(LocationDto locationDto, Principal principal) {
            Authentication authentication = (Authentication) principal;
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            logger.info("Authenticated user inside ChatMessageService Method: " + userDetails.getUsername());
            Location location = convertToLocation(locationDto);
            location.setSenderId(userDetails.getUsername());
            logger.info("In ChatMessageService Save" + location);
        var chatId = chatRoomService.getChatRoomId(location.getSenderId(), location.getRecipientId(),true)
                .orElseThrow(()->new WebSocketRelatedException("Unable to fetch chat Room Id"));
        location.setChatId(chatId);
        locationRepo.save(location);
        logger.info(location.toString());
        return location;
    }

    public List<Location> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId,recipientId,false);
        return chatId.map(locationRepo::findByChatId).orElse(new ArrayList<>());
    }

    private Location convertToLocation(LocationDto locationDto) {
        return modelMapper.map(locationDto, Location.class);
    }

    private LocationDto convertToLocationDto(Location location) {
        return modelMapper.map(location, LocationDto.class);
    }

    @PostConstruct
    private void configureMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(LocationDto.class, Location.class).addMappings(mapper->{
          mapper.skip(Location::setId);
        });
    }
}
