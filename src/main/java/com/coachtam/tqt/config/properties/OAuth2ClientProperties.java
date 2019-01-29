package com.coachtam.tqt.config.properties;

import lombok.Data;

/**
 * Created on 2018/1/24 0024.
 *
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private Integer accessTokenValiditySeconds = 7200;
}
