package com.dmrj.dm_resqjeevan.dto;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebSocketDto {
    private WebSocketSession webSocketSession;
    private String webSocketSessionId;
}
