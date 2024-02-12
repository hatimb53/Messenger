package com.hb.messenger.models.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthResponse {
    String token;
}
