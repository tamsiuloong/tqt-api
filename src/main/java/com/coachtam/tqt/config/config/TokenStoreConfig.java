package com.coachtam.tqt.config.config;

import com.coachtam.tqt.config.properties.OAuth2Properties;
import com.coachtam.tqt.config.security.jwt.MyJwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@Configuration
public class TokenStoreConfig {



    /**
     * 用于存放token
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * jwt TOKEN配置信息
     */
    @Configuration
    @ConditionalOnProperty(prefix = "tqt.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenCofnig{

        @Autowired
        private OAuth2Properties oAuth2Properties;

        /**
         * 使用jwtTokenStore存储token
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 用于生成jwt
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());//生成签名的key
            return accessTokenConverter;
        }

        /**
         * 用于扩展JWT
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new MyJwtTokenEnhancer();
        }

    }
}
