package com.steady.steadyback.dto;

import com.steady.steadyback.domain.ChatMessage;
import com.steady.steadyback.domain.ChatRoom;
import com.steady.steadyback.domain.User;
import lombok.*;
import org.springframework.messaging.Message;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {

    @Getter
    public static class Send {
        private String message;
        private Long senderId;
        private Long receiverId;
        private String roomId;

        public ChatMessage toMessage() {
            return ChatMessage.builder()
                    .message(message)
                    .sender(User.builder().id(senderId).build())
                    .chatRoom(ChatRoom.builder().roomId(roomId).build())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String message;
        private UserResponseDto sender;
        private LocalDateTime sendTime;

        public static Response of(ChatMessage message) {
            return Response.builder()
                    .message(message.getMessage())
                    .sender(new UserResponseDto(message.getSender()))
                    .sendTime(message.getSendTime())
                    .build();
        }
    }

//    private Long id;
//    private ChatRoom chatRoom;
//    private User sender;
//    private String message;
}
