package com.shahintraining.customer.enums;

import java.util.Arrays;
import java.util.List;

public enum AllowedUrls {

    LOGIN("/api/v1/customer/login"),
    RESEND_TOKEN("/api/v1/customer/resend-token"),
    REFRESH_TOKEN("/api/v1/customer/refresh-token/**"),
    VERIFY("/api/v1/customer/verify-customer"),
    REGISTER("/api/v1/customer/register");

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
