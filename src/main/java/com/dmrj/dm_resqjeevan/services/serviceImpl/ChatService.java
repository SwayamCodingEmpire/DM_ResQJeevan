package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final OnlineOfflineServiceImpl currentlyOnlineService;

    public ChatService(SimpMessageSendingOperations simpMessageSendingOperations, OnlineOfflineServiceImpl currentlyOnlineService) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.currentlyOnlineService = currentlyOnlineService;
    }

    public void sendMessageToConvId(ChatMessage chatMessage, String conversationId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        UserDetails userDetails = getUser();
        populateContext(chatMessage,userDetails);
//        Boolean istargetOnline = currentlyOnlineService;
    }

    public UserDetails getUser(){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDetails) object;
    }

    private void populateContext(ChatMessage chatMessage,UserDetails userDetails){
        chatMessage.setSenderUsername(userDetails.getUsername());
    }
}
