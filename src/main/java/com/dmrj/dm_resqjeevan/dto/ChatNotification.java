package com.dmrj.dm_resqjeevan.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatNotification {
    private long id;
    private String senderId;
    private String recipientId;
    private double lat;
    private double lng;
}
