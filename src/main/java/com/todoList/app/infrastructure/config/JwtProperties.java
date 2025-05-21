package com.todoList.app.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private String secret;
    private long expiration;

    public void setSecret(String secret) {
        this.secret = secret;
    }
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
    public String getSecret() {
        return secret;
    }
    public long getExpiration() {
        return expiration;
    }
}
