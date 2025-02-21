package com.meet.aibot.controller;

import com.meet.aibot.dto.Message;
import com.meet.aibot.models.Chat;
import com.meet.aibot.models.User;
import com.meet.aibot.service.UserService;
import com.meet.aibot.utils.UserUtil;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/chats")
public class ChatsController {

    private final ChatClient client;
    private final UserUtil userUtil;
    private final UserService userService;


    public ChatsController(ChatClient.Builder chatClient, UserUtil userUtil, UserService  userService) {
        this.client = chatClient.build();
        this.userUtil = userUtil;
        this.userService = userService;
        System.out.println("Client is created");
    }

    @GetMapping("/all-chats")
    public ResponseEntity<?> getChats() {
        User user = userUtil.getUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(user.getChats());
    }

    @DeleteMapping("/delete-chats")
    public ResponseEntity<?> deleteChats() {
        User user = userUtil.getUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        user.getChats().clear();
        userService.updateUser(user);
        return ResponseEntity.ok("Chats Deleted successfully !");
    }

    @PostMapping("/new")
    public ResponseEntity<?> generateChatCompletion(@Valid @RequestBody Message message) {
        User user = userUtil.getUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            List<Chat> chats = user.getChats();
            chats.add(new Chat("user", message.message()));

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

            return ResponseEntity.ok(userService.updateUser(user).getChats());
        } catch (Exception e) {
            log.error("e: ", e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

