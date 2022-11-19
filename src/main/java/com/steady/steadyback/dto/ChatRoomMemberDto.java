package com.steady.steadyback.dto;

import com.steady.steadyback.domain.ChatRoom;
import com.steady.steadyback.domain.User;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMemberDto {

    private Long id;
    private ChatRoom chatRoom;
    private User user;

}
