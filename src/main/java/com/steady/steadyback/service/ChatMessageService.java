package com.steady.steadyback.service;

import com.steady.steadyback.domain.ChatMessageRepository;
import com.steady.steadyback.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(ChatMessageDto.Send message) {
        chatMessageRepository.save(message.toMessage());
    }

}