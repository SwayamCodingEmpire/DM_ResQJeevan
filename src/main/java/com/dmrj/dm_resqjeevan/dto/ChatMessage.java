package com.dmrj.dm_resqjeevan.dto;


import com.dmrj.dm_resqjeevan.helpers.MessageDeliveryStatus;
import com.dmrj.dm_resqjeevan.helpers.MessageDeliveryStatusUpdate;
import com.dmrj.dm_resqjeevan.helpers.MessageType;
import com.dmrj.dm_resqjeevan.helpers.UserConnection;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatMessage {
    private String content;
    private MessageType messageType;
    private String senderUsername;
    private String receiverUsername;
    private UserConnection userConnection;
    private MessageDeliveryStatus messageDeliveryStatus;
    private List<MessageDeliveryStatusUpdate> messageDeliveryStatusUpdates;
}
