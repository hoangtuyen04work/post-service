package com.service.post_service.config;

import com.commons.commonscore.exception.GlobalException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public GlobalException globalException() {
        return new GlobalException();
    }
}
