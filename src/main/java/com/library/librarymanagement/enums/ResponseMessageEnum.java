package com.library.librarymanagement.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseMessageEnum {
    STORE_SUCCESS("OK"),
    STORE_FAILED("Failed"),
    CATEGORY_NOT_FOUND("Category not found!"),
    SERVER_PROBLEM("Something went wrong. Contact Support"),
    ISBN_REGEX_INVALID("Error isbn number is in wrong format."),
    MISSING_CATEGORIES_REQ("Error, no tags provided.");


    private final String message;

    ResponseMessageEnum(String message) {
        this.message = message;
    }

    @JsonValue
    public String toValue() {
        return message;
    }
}
