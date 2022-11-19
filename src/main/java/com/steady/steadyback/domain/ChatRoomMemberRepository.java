package com.steady.steadyback.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    public List<ChatRoomMember> findAllByUser(User user);
    public void deleteChatRoomMemberByChatRoom_IdAndAndUser(String roomId, User user);
}

