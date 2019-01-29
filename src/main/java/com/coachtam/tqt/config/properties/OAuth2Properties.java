package com.coachtam.tqt.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "tqt.security.oauth2")
public class OAuth2Properties {

    private String jwtSigningKey = "tqt";

    private OAuth2ClientProperties[] clients = {};
}
