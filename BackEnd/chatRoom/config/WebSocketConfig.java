package com.chatRoom.chatRoom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/sessions");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Bean("doSomethingExecutor")
    public Executor doSomethingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // core thread: thread number of threads initialized during thread pool
        executor.setCorePoolSize(10);
        // Maximum thread: The maximum number of thread pools, only after the buffer queue is full, the thread exceeding the core thread is applied.
        executor.setMaxPoolSize(20);
        // buffer queue: queue used to buffer execution tasks
        executor.setQueueCapacity(500);
        // Allow thread free time 60 seconds: When the thread beyond the core thread is destroyed after the idle time arrives
        executor.setKeepAliveSeconds(60);
        // Thread pool name prefix: After setting it, it can be convenient for our thread pool where the task is located.
        executor.setThreadNamePrefix("do-something-");
        // The buffer queue is full after the rejection policy: processing by calling thread (generally the main thread)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }
}
