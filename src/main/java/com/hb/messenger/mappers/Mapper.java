package com.hb.messenger.mappers;

import com.hb.messenger.models.response.ChatDto;
import com.hb.messenger.models.response.UnreadDirectMessagesDto;
import com.hb.messenger.models.response.UnreadGroupMessagesDto;
import java.util.List;

public class Mapper {

  public static UnreadDirectMessagesDto toUnreadDirectMessagesDto(String username,
      List<String> messages) {
    return UnreadDirectMessagesDto.builder().username(username).texts(messages).build();
  }

  public static UnreadGroupMessagesDto toUnreadGroupMessagesDto(String groupname,
      List<ChatDto> messages) {
    return UnreadGroupMessagesDto.builder().groupname(groupname).texts(messages).build();

  }

  public static ChatDto toChatDto(String message, String username) {
    return ChatDto.builder().message(message).username(username).build();
  }
}
