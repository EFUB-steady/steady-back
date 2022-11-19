package com.steady.steadyback.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(name = "chat_message")
@Entity
public class ChatMessage extends ChatBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private User receiver;

    @Column(nullable = false)
    private String message;



    @Builder
    public ChatMessage(ChatRoom chatRoom, User sender, User receiver, String message) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
    }
}
