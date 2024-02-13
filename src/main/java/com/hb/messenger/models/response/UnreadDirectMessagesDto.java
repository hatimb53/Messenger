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
public class UnreadDirectMessagesDto extends UnreadMessagesDto {

  String username;
  List<String> texts;
}
