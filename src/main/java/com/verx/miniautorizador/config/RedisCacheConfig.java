package com.verx.miniautorizador.config;

import org.springframework.beans.factory.annotation.Value;


public class RedisCacheConfig {
    @Value("${spring.redis.time-life}")
    private int timeLife;


}
