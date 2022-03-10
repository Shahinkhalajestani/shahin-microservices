package com.shahintraining.customer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * author - sh.khalajestani
 * created on - 3/9/2022
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigureProperties {
    private String secretKey;
    private Integer accessTokenExpireTimeInMinutes;
    private Integer refreshTokenExpireTimeInDays;
    private String tokenPrefix;
}
