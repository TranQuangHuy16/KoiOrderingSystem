package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.ChatMessage;
import com.project.KoiOrderingSystem.model.AccountResponse;
import com.project.KoiOrderingSystem.service.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class ChatMessageAPI {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    public void receiveChat(@Payload ChatMessage message) {
        System.out.println("Nhận được message: " + message.getContent());
        chatService.handleChatMessage(message);
    }

    @GetMapping
    public List<ChatMessage> getMessages(
            @RequestParam String sender,
            @RequestParam String receiver
    ) {
        return chatService.getChatHistory(sender, receiver);
    }

}
