package com.jerryxie.redfin.configurations;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfiguration {

    private final int threadPoolSize = 200;

    @Bean
    public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        return new ScheduledThreadPoolExecutor(threadPoolSize);
    }
}
