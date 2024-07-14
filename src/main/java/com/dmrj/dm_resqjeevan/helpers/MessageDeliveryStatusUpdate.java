package com.dmrj.dm_resqjeevan.helpers;

import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class MessageDeliveryStatusUpdate {
    private UUID id;
    private String content;
    private MessageDeliveryStatus status;
}
