package com.whatsappclone.service;

import com.whatsappclone.dto.MessageRequest;
import com.whatsappclone.dto.MessageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {

    void saveMessage(MessageRequest messageRequest);

    List<MessageResponse> findChatMessages(String chatId);

    void setMessagesToSeen(String chatId, Authentication authentication);

    void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) throws IOException;
}

