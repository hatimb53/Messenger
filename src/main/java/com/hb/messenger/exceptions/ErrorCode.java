package com.hb.messenger.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATE_USER("User already exists"),

    USER_NOT_FOUND("user not found");

    private final String message;
}
