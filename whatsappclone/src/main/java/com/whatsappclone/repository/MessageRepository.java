package com.whatsappclone.repository;

import com.whatsappclone.chat.Chat;
import com.whatsappclone.message.Message;
import com.whatsappclone.message.MessageConstants;
import com.whatsappclone.message.MessageState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    List<Message> findMessagesByChatId(String chatId);

    @Modifying
    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    void setMessagesToSeenByChatId(@Param("chatId") String chatId,
                                   @Param("newState") MessageState messageState);
}
