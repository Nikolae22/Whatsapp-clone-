package com.whatsappclone.service;

import com.whatsappclone.chat.Chat;
import com.whatsappclone.dto.ChatResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ChatService {

    List<ChatResponse> getChatsByReceiverId(Authentication currentUser);
    String createChat (String senderId,String receiverId);


}
