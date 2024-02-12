package com.hb.messenger.services;


import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.entities.ChatEntity;
import com.hb.messenger.models.response.Chat;
import com.hb.messenger.models.response.ChatHistory;
import com.hb.messenger.models.response.ReceivedMessage;
import com.hb.messenger.repositories.ChatRepository;
import com.hb.messenger.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public void sendMessage(String to, String from, String message) {

        if (userRepository.findById(to).isPresent() && userRepository.findById(from).isPresent()) {
            chatRepository.save(ChatEntity.builder().to(to).from(from).message(message).isRead(false).build());
        }
        else{
            throw MessengerException.error(ErrorCode.USER_NOT_FOUND);
        }
    }

    public List<ReceivedMessage> fetchUnreadMessages(String username) {
        if (userRepository.findById(username).isPresent()) {
            List<ChatEntity> messageEntities = chatRepository.fetchUnreadMessages(username);
            messageEntities.forEach(x -> x.setRead(true));
            chatRepository.saveAll(messageEntities);
            Map<String, List<String>> messages = messageEntities.stream().collect(Collectors.groupingBy(ChatEntity::getFrom,
                    Collectors.mapping(ChatEntity::getMessage, Collectors.toList())));
            return messages.entrySet().stream().map(x -> ReceivedMessage.builder().username(x.getKey()).texts(x.getValue()).build())
                    .collect(Collectors.toList());
        }

        throw MessengerException.error(ErrorCode.USER_NOT_FOUND);
    }

    public ChatHistory fetchChatHistory(String username1, String username2) {
        if (userRepository.findById(username1).isPresent() && userRepository.findById(username2).isPresent()) {
            List<ChatEntity> messageEntities = chatRepository.fetchChatHistory(username1, username2);
            return ChatHistory.builder().texts(messageEntities.stream().map(x -> Chat.builder().username(x.getFrom())
                            .message(x.getMessage()).build()).collect(Collectors.toList())).build();

        }

        throw MessengerException.error(ErrorCode.USER_NOT_FOUND);
    }


}
