package com.share.springboot.common;

/**
 * @author Yu W
 * @date 2020/5/15 21:37
 */

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @功能描述 内存缓存
 * @author www.gaozz.club
 * @date 20120-04-26
 */
@Configuration
public class UrlCache {
    @Bean
    public Cache<String, Integer> getCache() {
        return CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.SECONDS).build();// 缓存有效期为2秒
    }
}
