package com.whatsappclone.mapper;

import com.whatsappclone.dto.MessageResponse;
import com.whatsappclone.message.Message;
import com.whatsappclone.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageMapper {


    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedDate())
                .media(FileUtils.readFileFromLocation(message.getMediaFilePath()))
                .build();
    }
}
