package com.drop.assignment.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for rate limiter
 * @author Bijaya Bhaskar Swain
 */
@Configuration
public class BucketConfiguration {

    @Value("${bucket.size}")
    private int bucketSize;

    @Bean
    public Bucket bucket(){
        Bandwidth limit = Bandwidth.classic(bucketSize, Refill.greedy(bucketSize, Duration.ofMinutes(1)));
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

}
