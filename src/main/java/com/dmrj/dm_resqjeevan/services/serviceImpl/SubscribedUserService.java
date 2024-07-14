package com.dmrj.dm_resqjeevan.services.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class SubscribedUserService {
    private final Map<UUID, Set<String>> usersSubscribed;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public SubscribedUserService(Map<UUID, Set<String>> usersSubscribed, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.usersSubscribed = usersSubscribed;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    private UserDetails getUserDetails(Principal principal) {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
        Object object = user.getPrincipal();
        return (UserDetails) object;
    }
    public void addUserSubscribed(Principal user, String subscribedChannel) {
        UserDetails userDetails = getUserDetails(user);
        log.info("{} subscribed to {}", userDetails.getUsername(), subscribedChannel);
//        Set<String> subscriptions = usersSubscribed.getOrDefault(userDetails.ge)
    }
}
