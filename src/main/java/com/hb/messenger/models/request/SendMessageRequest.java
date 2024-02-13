package com.hb.messenger.models.request;

import com.hb.messenger.models.enums.ChatType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMessageRequest {
    String to;
    ChatType type;
    String text;
}
