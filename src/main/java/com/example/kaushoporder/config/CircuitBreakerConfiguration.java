package com.example.kaushoporder.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {
    @Bean
    /**
     * 서킷 브레이커 설정 설명 - 아래 개별 설정 값의 주석과 비교하며 읽어주세요
     *
     * 최근 b초 동안 발생한 호출 수를 l, 그중 실패한 요청의 수를 h, 느린 요청의 수를 i라고 할때
     * h/l >= c 이거나 i/l >= g 이면 OPEN
     * OPEN 된 상태로 d 시간동안 무조건 not permitted call 처리
     * d 시간이 경과된 후 새로운 요청이 들어오면 HALF-OPEN 상태로 전환
     * (즉, OPEN 된 상태로 아무런 요청도 들어오지 않으면 HALF-OPEN 되지 않음)
     * HALF-OPEN 상태에서 연속된 e회의 호출 중 실패한 요청 수를 j, 느린 요청 수를 k라고 할때
     * j/e >= c 이거나 k/e >= g 이면 다시 OPEN, 아니면 CLOSE
     */
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(
                        CircuitBreakerConfig.custom()
                                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                .minimumNumberOfCalls(5)                            // 최소 a회 이상의 호출이 발생해야 작동함
                                .slidingWindowSize(10)                              // b초 동안 연속된 호출 결과를 기준으로 판단
                                .failureRateThreshold(60)                           // 실패한 요청의 비율이 c%에 도달하면 OPEN
                                .waitDurationInOpenState(Duration.ofSeconds(10))    // OPEN 된 상태로 d시간 동안 대기 후 HALF-OPEN
                                .permittedNumberOfCallsInHalfOpenState(5)           // HALF-OPEN 상태에서 e회의 연속된 호출 결과를 기준으로 판단
                                .slowCallDurationThreshold(Duration.ofSeconds(1))   // f시간 이상 걸린 요청은 느린 요청으로 카운트
                                .slowCallRateThreshold(100)                         // 느린 요청의 비율이 g%에 도달하면 OPEN
                                .build()
                )
                .timeLimiterConfig(
                        TimeLimiterConfig.custom()
                                .timeoutDuration(Duration.ofSeconds(2))
                                .build()
                )
                .build()
        );
    }
}