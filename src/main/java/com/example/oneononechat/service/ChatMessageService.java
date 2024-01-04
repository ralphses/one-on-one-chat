package com.example.oneononechat.service;

import com.example.oneononechat.dao.ChatMessageDao;
import com.example.oneononechat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageDao chatMessageDao;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage message) {
        String chatId = chatRoomService.getChatRoomId(message.getSenderId(), message.getRecipientId(), true).orElseThrow();
        message.setChatId(chatId);
        return chatMessageDao.save(message);
    }

    public List<ChatMessage> findMessages(String senderId, String recipientId) {
        return chatRoomService.getChatRoomId(senderId, recipientId, false)
                .map(chatMessageDao::findByChatId)
                .orElse(emptyList());
    }
}
