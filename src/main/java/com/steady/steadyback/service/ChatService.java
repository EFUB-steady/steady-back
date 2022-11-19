package com.steady.steadyback.service;


import com.steady.steadyback.domain.*;
import com.steady.steadyback.dto.ChatRoomDto;
import com.steady.steadyback.dto.ChatRoomMemberDto;
import com.steady.steadyback.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserRepository userRepository;

    //유저가 있는 전체 채팅방 조회
    public List<ChatRoomDto> findMyChatRooms(User user) {
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findAllByUser(user);
        List<ChatRoomDto> chatRoomDtos = new ArrayList<>();
        for(ChatRoomMember chatRoomMember : chatRoomMembers) {
            ChatRoom chatRoom = chatRoomMember.getChatRoom();
            ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                    .id(chatRoom.getId())
                    .roomId(chatRoom.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .build();

            chatRoomDtos.add(chatRoomDto);
        }

        return chatRoomDtos;
    }

    //roomId 기준으로 채팅방 찾기
    public ChatRoomDto findRoomByRoomId(String roomId) {
        ChatRoom chatRoom =  chatRoomRepository.findChatRoomByRoomId(roomId);
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .build();
    }

    //roomName으로 채팅방 만들기 + 만든 유저 자동 가입
    public void createChatRoom(String roomName, User user) {
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(chatRoomDto.getRoomId())
                .roomName(chatRoomDto.getRoomName())
                .build();

        chatRoomRepository.save(chatRoom);

        ChatRoomMemberDto chatRoomMemberDto = ChatRoomMemberDto.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .chatRoom(chatRoomMemberDto.getChatRoom())
                .user(chatRoomMemberDto.getUser())
                .build();

        chatRoomMemberRepository.save(chatRoomMember);

        //return "The New Room was Created!";
    }

    //채팅방 삭제 - ChatRoomMember 관계 삭제
    public String deleteChatRoom(String roomId, User user) {
        chatRoomMemberRepository.deleteChatRoomMemberByChatRoom_IdAndAndUser(roomId, user);
        return "The Room was Deleted!";
    }

    //채팅방 입장
    public String enterChatRoom(String roomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByRoomId(roomId);
        ChatRoomMemberDto chatRoomMemberDto = ChatRoomMemberDto.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

        chatRoomMemberRepository.save(ChatRoomMember.builder()
                        .chatRoom(chatRoomMemberDto.getChatRoom())
                        .user(user)
                        .build());

        return user.getId().toString();
    }

    //채팅방 전체 userList 조회
//    public List<UserResponseDto> findAllUsersInRoom(String roomId) {
//        ChatRoom chatRoom = chatRoomRepository.findChatRoomByRoomId(roomId);
//        chatRoomMemberRepository.findAll
//    }
}
