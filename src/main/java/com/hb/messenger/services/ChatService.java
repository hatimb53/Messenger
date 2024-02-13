package com.hb.messenger.services;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.entities.Chat;
import com.hb.messenger.models.entities.GroupInfo;
import com.hb.messenger.models.entities.UnreadMessages;
import com.hb.messenger.models.entities.UserInfo;
import com.hb.messenger.models.enums.ChatType;
import com.hb.messenger.models.response.ChatDto;
import com.hb.messenger.models.response.UnreadDirectMessagesDto;
import com.hb.messenger.models.response.UnreadGroupMessagesDto;
import com.hb.messenger.models.response.UnreadMessagesDto;
import com.hb.messenger.repositories.ChatRepository;
import com.hb.messenger.repositories.GroupRepository;
import com.hb.messenger.repositories.UnreadMessageRepository;
import com.hb.messenger.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

  private final ChatRepository chatRepository;

  private final UserRepository userRepository;

  private final UnreadMessageRepository unreadMessageRepository;

  private final GroupRepository groupRepository;

  public ChatService(ChatRepository chatRepository, UserRepository userRepository,
      UnreadMessageRepository unreadMessageRepository, GroupRepository groupRepository) {
    this.chatRepository = chatRepository;
    this.userRepository = userRepository;
    this.unreadMessageRepository = unreadMessageRepository;
    this.groupRepository = groupRepository;
  }

  private void saveUnreadMessage(String username, Chat chat) {
    Optional<UnreadMessages> unreadMessages = unreadMessageRepository.fetchUnreadMessages(username);

    if (unreadMessages.isPresent()) {
      unreadMessages.get().getChatList().add(chat);
      unreadMessageRepository.save(unreadMessages.get());
    } else {
      unreadMessageRepository.save(
          UnreadMessages.builder().user(userRepository.findById(username).get())
              .chatList(List.of(chat)).build());
    }
  }

  public void sendMessage(String to, String from, String message, ChatType type) {

    Optional<UserInfo> userInfo = userRepository.findById(from);
    if (userInfo.isEmpty()) {
      throw MessengerException.error(ErrorCode.USER_NOT_FOUND, from);
    }
    Chat chat = chatRepository.save(
        Chat.builder().to(to).from(from).message(message).type(type).build());

    switch (type) {
      case DIRECT -> {
        saveUnreadMessage(to, chat);
      }
      case GROUP -> {
        Optional<GroupInfo> group = groupRepository.findById(to);
        if (group.isEmpty()) {
          throw MessengerException.error(ErrorCode.GROUP_NOT_FOUND, to);
        }
        Set<String> userBelongsToGroups = userInfo.get().getGroups().stream()
            .map(GroupInfo::getName).collect(Collectors.toSet());

        if (!userBelongsToGroups.contains(to)) {
          throw MessengerException.error(ErrorCode.USER_NOT_BELONGS_TO_GROUP);
        }

        for (UserInfo user : group.get().getUsers()) {
          if (!user.getUsername().equals(from)) {
            saveUnreadMessage(user.getUsername(), chat);
          }
        }
      }
    }

  }

  public List<UnreadMessagesDto> fetchUnreadMessages(String username) {

    Optional<UnreadMessages> unreadMessages = unreadMessageRepository.fetchUnreadMessages(username);

    if (unreadMessages.isPresent()) {
      Map<String, List<String>> directMessages = unreadMessages.get().getChatList().stream()
          .filter(chat -> chat.getType().equals(ChatType.DIRECT))
          .collect(Collectors.groupingBy(Chat::getFrom,
              Collectors.mapping(Chat::getMessage, Collectors.toList())));

      Map<String, List<ChatDto>> groupMessages = unreadMessages.get().getChatList().stream()
          .filter(chat -> chat.getType().equals(ChatType.GROUP))
          .collect(Collectors.groupingBy(Chat::getTo,
              Collectors.mapping(
                  x -> ChatDto.builder().message(x.getMessage()).username(x.getFrom()).build(),
                  Collectors.toList())));

      List<UnreadMessagesDto> unreadMessagesDto = new ArrayList<>();

      unreadMessagesDto.addAll(directMessages.entrySet().stream().map(x ->
              UnreadDirectMessagesDto.builder().username(x.getKey()).texts(x.getValue()).build())
          .toList());

      unreadMessagesDto.addAll(groupMessages.entrySet().stream().map(x ->
              UnreadGroupMessagesDto.builder().groupname(x.getKey()).texts(x.getValue()).build())
          .toList());

      unreadMessageRepository.delete(unreadMessages.get());
      return unreadMessagesDto;
    }
    return Collections.emptyList();

  }

  public List<ChatDto> fetchChatHistory(String username1, String username2) {
    if (userRepository.findById(username1).isEmpty()) {
      throw MessengerException.error(ErrorCode.USER_NOT_FOUND, username1);
    }
    if (userRepository.findById(username2).isEmpty()) {
      throw MessengerException.error(ErrorCode.USER_NOT_FOUND, username2);
    }

    return chatRepository.fetchChatHistory(username1, username2).stream()
        .map(x -> ChatDto.builder().username(x.getFrom()).message(x.getMessage()).build())
        .collect(Collectors.toList());


  }

  public List<ChatDto> fetchGroupChatHistory(String groupname) {

    if (groupRepository.findById(groupname).isEmpty()) {
      throw MessengerException.error(ErrorCode.GROUP_NOT_FOUND, groupname);
    }
    return chatRepository.fetchGroupChatHistory(groupname).stream()
        .map(x -> ChatDto.builder().username(x.getFrom()).message(x.getMessage()).build())
        .collect(Collectors.toList());

  }

}
