package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.ChatMessage;
import com.project.KoiOrderingSystem.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void handleChatMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());

        // Lưu DB
        chatRepo.save(message);

        // Gửi tới người nhận
        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );
    }

    public List<ChatMessage> getChatHistory(String user1, String user2) {
        return chatRepo.findBySenderAndReceiver(user1, user2); // hoặc query theo cả 2 chiều
    }
}
