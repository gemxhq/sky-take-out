package com.sky.service.impl;

import com.sky.service.ShopService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShopServiceImpl implements ShopService {
    public static String KEY = "SHOP_STATUS";

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Integer getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        return status;
    }

    @Override
    public void setStatus(int status) {
        redisTemplate.opsForValue().set(KEY, status);
    }
}
