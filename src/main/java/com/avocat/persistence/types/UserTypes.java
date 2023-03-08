package com.avocat.persistence.types;

import lombok.Getter;

@Getter
public enum UserTypes {
    ACTIVE,
    FORGOT_PASSWORD,
    BLOCKED,
    FIRST_ACCESS;
}
