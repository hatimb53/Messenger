package com.hb.messenger.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATE_USER("User already exists"),

    USER_NOT_FOUND("User not found"),

    AUTH_ERROR("user or passcode is incorrect");

    private final String message;
}
