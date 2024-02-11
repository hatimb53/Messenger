package com.hb.messenger.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    SUCCES("success"),
    FAILURE("failure");

    private String name;

}
