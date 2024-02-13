package com.hb.messenger.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UnreadGroupMessagesDto extends UnreadMessagesDto {

  String groupname;
  List<ChatDto> texts;
}
