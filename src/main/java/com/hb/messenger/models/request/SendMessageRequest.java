package com.hb.messenger.models.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMessageRequest {
    String to;
    String text;
}
