package com.hb.messenger.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  DUPLICATE_USER(409, "User already exists"),

  DUPLICATE_GROUP(409, "Group already exists"),

  USER_NOT_FOUND(404, "User not found"),

  AUTHENTICATION_ERROR(401, "user or passcode is incorrect"),

  AUTHORIZATION_ERROR(403, "not authorized"),

  GROUP_NOT_FOUND(404, "group not found"),

  USER_NOT_BELONGS_TO_GROUP(403, "user not belongs to group"),

  USER_ALREADY_EXIST(409, "user already exist in group"),

  USER_NOT_EXIST(404, "user not exist in group");

  private final int status;

  private final String message;
}
