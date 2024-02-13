package com.hb.messenger.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UnreadDirectMessagesDto extends UnreadMessagesDto {
    String username;
    List<String> texts;
}
