package com.steady.steadyback.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    public ChatRoom findChatRoomByRoomId(String roomId);
}
