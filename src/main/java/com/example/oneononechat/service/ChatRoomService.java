package com.example.oneononechat.service;

import com.example.oneononechat.dao.ChatRoomDao;
import com.example.oneononechat.model.ChatRoom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomDao chatRoomDao;

    public Optional<String> getChatRoomId(String senderId, String recipientId, boolean createIfNotExist) {
        return chatRoomDao.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId).or(() -> {
                    if (createIfNotExist) {
                        return Optional.of(createRooms(senderId, recipientId));
                    }
                    return Optional.empty();
                });
    }

    private String createRooms(String senderId, String recipientId) {

        String chatId = String.format("%s_%s", recipientId, senderId);

        ChatRoom chatRoomRecipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom chatRoomSenderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomDao.save(chatRoomRecipientSender);
        chatRoomDao.save(chatRoomSenderRecipient);

        return chatId;
    }
}
