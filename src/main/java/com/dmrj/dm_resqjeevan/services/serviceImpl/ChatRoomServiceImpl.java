package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.entities.ChatRoom;
import com.dmrj.dm_resqjeevan.repositories.ChatRoomRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ChatRoomServiceImpl {
    private final ChatRoomRepo chatRoomRepo;

    public ChatRoomServiceImpl(ChatRoomRepo chatRoomRepo) {
        this.chatRoomRepo = chatRoomRepo;
    }

    public Optional<String> getChatRoomId(String senderId, String receiverId,Boolean createNewRoomIfDoesNotExist) {
        return chatRoomRepo.findChatRoomsBySenderIdAndRecipientId(senderId,receiverId)
                .map(ChatRoom::getChatId)
                .or(()->{
                    if(createNewRoomIfDoesNotExist) {
                        var chatId = createChatId(senderId,receiverId);

                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String recipientId) {
        var senderChatId = String.format("%s_%s", senderId, recipientId);
        var receiverChatId = String.format("%s_%s", recipientId, senderId);
        ChatRoom senderRecipient = ChatRoom.builder()
                .id(senderChatId)
                .chatId(senderChatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
        ChatRoom recipientSender = ChatRoom.builder()
                .id(receiverChatId)
                .chatId(receiverChatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepo.save(senderRecipient);
        chatRoomRepo.save(recipientSender);
        return senderChatId;
    }

}
