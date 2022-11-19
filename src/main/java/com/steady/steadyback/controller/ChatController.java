package com.steady.steadyback.controller;

import com.steady.steadyback.common.BasicResponse;
import com.steady.steadyback.common.Result;
import com.steady.steadyback.domain.ChatMessage;
import com.steady.steadyback.domain.User;
import com.steady.steadyback.dto.ChatDto;
import com.steady.steadyback.dto.ChatMessageDto;
import com.steady.steadyback.dto.ChatRoomDto;
import com.steady.steadyback.service.ChatMessageService;
import com.steady.steadyback.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final ChatMessageService messageService;

    @MessageMapping("/chat/send")
    public void chat(ChatMessageDto.Send message) {
        messageService.sendMessage(message);
        messagingTemplate.convertAndSend("/topic/chat/" + message.getReceiverId(), message);
    }

    @PostMapping("/chat/enterUser")
    public ResponseEntity<BasicResponse> enterUser(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal User user) {
        String userId = chatService.enterChatRoom(chatRoomDto.getRoomId(), user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>(userId));
    }

    @PostMapping("/chat/createRoom")
    public String createRoom(@RequestParam("roomName") String name,
                             @AuthenticationPrincipal User user
                             ) {
        chatService.createChatRoom(name, user);
        return "success";
    }

}
