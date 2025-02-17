package com.meet.aibot.controller;

import com.meet.aibot.models.Chat;
import com.meet.aibot.models.User;
import com.meet.aibot.service.UserService;
import com.meet.aibot.utils.UserUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "http://localhost:5173/chat", allowCredentials = "true")
public class ChatsController {

    private final ChatClient client;
    private final UserUtil userUtil;
    private final UserService userService;


    public ChatsController(ChatClient.Builder chatClient, UserUtil userUtil, UserService  userService) {
        this.client = chatClient.build();
        this.userUtil = userUtil;
        this.userService = userService;
    }

    @PostMapping("/new")
    @Validated
    public ResponseEntity<?> generateChatCompletion(@NotBlank @RequestBody String message) {
        User user = userUtil.getUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            List<Chat> chats = user.getChats();
            chats.add(new Chat("user", message));

            var promptBuilder = client.prompt();

            // Add all previous messages from chat history
            for (Chat chat : user.getChats()) {
                if ("user".equals(chat.getRole())) {
                    promptBuilder.user(chat.getContent());
                } else if ("assistant".equals(chat.getRole())) {
                    promptBuilder.messages(new AssistantMessage(chat.getContent()));
                }
            }

            ChatClient.CallResponseSpec chatResponse = promptBuilder.call();
            String response = chatResponse.content();
            chats.add(new Chat("assistant", response));
            userService.updateUser(user);
            return ResponseEntity.ok(user.getChats());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


}
