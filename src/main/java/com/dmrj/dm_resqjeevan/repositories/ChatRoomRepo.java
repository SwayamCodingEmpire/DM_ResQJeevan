package com.dmrj.dm_resqjeevan.repositories;

import com.dmrj.dm_resqjeevan.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom,String> {
    Optional<ChatRoom> findChatRoomsBySenderIdAndRecipientId(String senderId, String recipientId);
}
