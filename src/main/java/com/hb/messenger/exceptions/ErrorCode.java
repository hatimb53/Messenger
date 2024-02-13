package com.hb.messenger.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATE_USER("User already exists"),

    DUPLICATE_GROUP("Group already exists"),

    USER_NOT_FOUND("User not found"),

    AUTH_ERROR("user or passcode is incorrect"),

    GROUP_NOT_FOUND("group not found"),

    USER_NOT_BELONGS_TO_GROUP("user not belongs to group"),

    USER_ALREADY_EXIST("user already exist in group"),

    USER_NOT_EXIST("user not exist in group");

    private final String message;
}
