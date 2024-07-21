package com.dmrj.dm_resqjeevan.config;

import com.dmrj.dm_resqjeevan.excpetions.WebSocketRelatedException;
import com.dmrj.dm_resqjeevan.services.serviceImpl.ControlRoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private final Map<String,String> simpSessionSubscriptionId = new ConcurrentHashMap<>();
    private final ControlRoomServiceImpl controlRoomService;

    public WebSocketEventListener(ControlRoomServiceImpl controlRoomService) {
        this.controlRoomService = controlRoomService;
    }



    @EventListener
    @SendToUser
    public void handleSubscribeEvent(SessionSubscribeEvent sessionSubscribeEvent){
        String subscribedChannel = (String) sessionSubscribeEvent.getMessage().getHeaders().get("simpDestination");
        String simpSessionId = (String) sessionSubscribeEvent.getMessage().getHeaders().get("simpSessionId");
        logger.info("Subscribed to channel "+subscribedChannel+" with id "+simpSessionId);
        if(subscribedChannel == null){
            logger.error("Subscribed channel to is NULL !!!???");
            return;
        }
        simpSessionSubscriptionId.put(simpSessionId,subscribedChannel);
    }

    @EventListener
    public void handleUnsubscribeEvent(SessionUnsubscribeEvent sessionUnsubscribeEvent){
        String simpSessionId = (String) sessionUnsubscribeEvent.getMessage().getHeaders().get("simpSessionId");
        String unSubscribedChannel = simpSessionSubscriptionId.get(simpSessionId);
        logger.info("Unsubscribed channel "+unSubscribedChannel+" with id "+simpSessionId);
    }

    @EventListener
    public void handleConnectedEvent(SessionConnectedEvent sessionConnectedEvent){
        logger.info("Connected to Connected event "+sessionConnectedEvent.getUser());
        try
        {
            Principal principal = sessionConnectedEvent.getUser();
            logger.info("Connected to user " + principal.getName());
            controlRoomService.makePersonnelOnline(principal.getName());
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.info("Unable to get Principal Object");
            throw new WebSocketRelatedException("Unable to fetch Principal Object");
        }
    }

    @EventListener
    public void handleDisconnectedEvent(SessionDisconnectEvent sessionDisconnectEvent ){
        logger.info("Disconnected from "+sessionDisconnectEvent.getUser());
        logger.info("Connected to Connected event "+sessionDisconnectEvent.getUser());
        try
        {
            Principal principal = sessionDisconnectEvent.getUser();
            logger.info("Disconnected to user " + principal.getName());
            controlRoomService.makePersonnelOffline(principal.getName());
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.info("Unable to get Principal Object");
            throw new WebSocketRelatedException("Unable to fetch Principal Object");
        }
    }
}
