package com.steady.steadyback.dto;

import lombok.*;

// Stomp 를 통해 pub/sub 를 사용하면 구독자 관리가 알아서 된다!!
// 따라서 따로 세션 관리를 하는 코드를 작성할 필도 없고,
// 메시지를 다른 세션의 클라이언트에게 발송하는 것도 구현 필요가 없다!
@Data
@Builder
@EqualsAndHashCode
@Getter
@Setter
public class ChatRoomDto {
    private Long id;
    private String roomId;
    private String roomName;
    // private int userCount; 채팅방 인원 수
}
