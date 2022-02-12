package ru.otus.pk.spring.config;

import io.github.resilience4j.circuitbreaker.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreaker circuitBreaker() {
        return CircuitBreakerRegistry.of(CircuitBreakerConfig.custom()
                        .slidingWindowType(COUNT_BASED)
                        .slidingWindowSize(3)
                        .slowCallRateThreshold(1f)
                        .slowCallDurationThreshold(Duration.ofSeconds(1))
                        .minimumNumberOfCalls(1)
                        .build())
                .circuitBreaker("circuitBreaker");
    }
}
