package com.tradevault.execution_service.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MarketPriceService {

    private final StringRedisTemplate redisTemplate;

    public MarketPriceService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public BigDecimal getPrice(String symbol) {
        String key = "price:" + symbol;
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            // No price set in Redis yet — fall back to a default mock price
            return new BigDecimal("100.00");
        }
        return new BigDecimal(value);
    }
}