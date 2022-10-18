package io.thundra.foresight.quotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class QuotesApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuotesApplication.class, args);
    }
}
