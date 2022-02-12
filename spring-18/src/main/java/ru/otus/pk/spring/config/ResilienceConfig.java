package ru.otus.pk.spring.config;

import io.github.resilience4j.circuitbreaker.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

@Configuration
public class ResilienceConfig {

    private static final CircuitBreakerConfig CONFIG = CircuitBreakerConfig.custom()
            .slidingWindowType(COUNT_BASED)
            .slidingWindowSize(3)
            .slowCallRateThreshold(1f)
            .slowCallDurationThreshold(Duration.ofSeconds(1))
            .minimumNumberOfCalls(1)
            .build();

    @Bean
    public CircuitBreaker bookCircuitBreaker() {
        return CircuitBreakerRegistry.of(CONFIG).circuitBreaker("bookCircuitBreaker");
    }

    @Bean
    public CircuitBreaker authorCircuitBreaker() {
        return CircuitBreakerRegistry.of(CONFIG).circuitBreaker("authorCircuitBreaker");
    }

    @Bean
    public CircuitBreaker genreCircuitBreaker() {
        return CircuitBreakerRegistry.of(CONFIG).circuitBreaker("genreCircuitBreaker");
    }
}
