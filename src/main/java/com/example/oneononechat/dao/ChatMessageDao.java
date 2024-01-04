package com.example.oneononechat.dao;

import com.example.oneononechat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageDao extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatId (String chatId);
}
