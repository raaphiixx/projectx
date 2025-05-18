package com.projectx.components;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheReset {
    
    @Scheduled(fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
    @CacheEvict(value = "users", allEntries = true)
    public void cleanUsers() {
        System.out.println("Users Cache Clean: " + LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 90, timeUnit = TimeUnit.SECONDS)
    @CacheEvict(value = "posts", allEntries = true)
    public void cleanPosts() {
        System.out.println("Posts Cache Clean: " + LocalDateTime.now());
    }
}
