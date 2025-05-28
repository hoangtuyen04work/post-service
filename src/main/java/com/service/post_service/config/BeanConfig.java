package com.service.post_service.config;

import com.commons.commons_security.TokenUtils;
import com.commons.commonscore.exception.GlobalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Value("${jwt.signerKey}")
    private String signerKey;
    @Bean
    public GlobalException globalException() {
        return new GlobalException();
    }
    @Bean
    public TokenUtils myService() {
        return new TokenUtils(signerKey);
    }
}
