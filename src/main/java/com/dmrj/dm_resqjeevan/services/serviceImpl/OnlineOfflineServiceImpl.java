package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.ChatMessage;
import com.dmrj.dm_resqjeevan.helpers.MessageType;
import com.dmrj.dm_resqjeevan.helpers.UserConnection;
import com.dmrj.dm_resqjeevan.repositories.CurrentlyOnlineRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OnlineOfflineServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(OnlineOfflineServiceImpl.class);
    private final Set<String> onlineusers;
    private final CurrentlyOnlineRepo currentlyOnlineRepo;
    private final Map<String,Set<String>> userSubscribed;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public OnlineOfflineServiceImpl(Set<String> onlineusers, CurrentlyOnlineRepo currentlyOnlineRepo, Map<String, Set<String>> userSubscribed, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.onlineusers = onlineusers;
        this.currentlyOnlineRepo = currentlyOnlineRepo;
        this.userSubscribed = userSubscribed;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    public Boolean addOnlineUser(Principal user) {
        if(user==null){
            return false;
        }
        UserDetails userDetails = getUserDetails(user);
        logger.info("{} is online",userDetails.getUsername());
        for(String onlineuser : onlineusers){
            simpMessageSendingOperations.convertAndSend("/topic/"+onlineuser, ChatMessage.builder()
                    .messageType(MessageType.ONLINE)
                    .userConnection(UserConnection.builder().connectionUsername(userDetails.getUsername()).build())
                    .build());
        }
        onlineusers.add(userDetails.getUsername());
        return true;
    }

    public Boolean removeOnlineUser(Principal user) {
        if(user==null){
            return false;
        }
        UserDetails userDetails = getUserDetails(user);
        logger.info("{} is offline",userDetails.getUsername());
        onlineusers.remove(userDetails.getUsername());
        userSubscribed.remove(userDetails.getUsername());
        for(String onlineuser : onlineusers){
            simpMessageSendingOperations.convertAndSend("/topic/"+onlineuser, ChatMessage.builder()
                    .messageType(MessageType.OFFLINE)
                    .userConnection(UserConnection.builder().connectionUsername(userDetails.getUsername()).build())
                    .build());
        }
        return true;
    }
//    public List<String> getOnlinePersonnels() {
//
//    }

    private UserDetails getUserDetails(Principal principal) {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
        Object object = user.getPrincipal();
        return (UserDetails) object;
    }
}
