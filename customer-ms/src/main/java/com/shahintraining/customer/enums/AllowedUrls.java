package com.shahintraining.customer.enums;

import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public enum AllowedUrls {

    LOGIN("/login"),
    SIGN_UP("/sign-up"),
    REFRESH_TOKEN("api/v1/customer/refresh-token");

    private final String value;

    AllowedUrls(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<String> getAllAllowedUrls() {
        return Arrays.stream(AllowedUrls.values()).map(AllowedUrls::getValue).toList();
    }

}
