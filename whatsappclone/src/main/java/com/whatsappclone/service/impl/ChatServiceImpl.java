package com.whatsappclone.service.impl;

import com.whatsappclone.chat.Chat;
import com.whatsappclone.dto.ChatResponse;
import com.whatsappclone.mapper.ChatMapper;
import com.whatsappclone.repository.ChatRepository;
import com.whatsappclone.repository.UserRepository;
import com.whatsappclone.service.ChatService;
import com.whatsappclone.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String userId=currentUser.getName();
        return chatRepository.findChatBySenderId(userId)
                .stream()
                .map(c->chatMapper.toChatResponse(c,userId))
                .toList();
    }

    @Override
    public String createChat(String senderId, String receiverId) {
        Optional<Chat> existingChat=chatRepository.findChatByReceiverAndSender(senderId,receiverId);
        if (existingChat.isPresent()){
            return existingChat.get().getId();
        }
        User sender=userRepository.findByPublicId(senderId)
                .orElseThrow(()->new EntityNotFoundException("User not found"));

        User receiver=userRepository.findByPublicId(receiverId)
                .orElseThrow(()->new EntityNotFoundException("User not found"));

        Chat chat=new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);

        Chat savedChat=chatRepository.save(chat);
        return savedChat.getId();
    }



}
