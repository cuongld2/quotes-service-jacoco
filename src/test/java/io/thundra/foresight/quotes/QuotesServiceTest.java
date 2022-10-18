package io.thundra.foresight.quotes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.support.RedisRepositoryFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
@Testcontainers
public class QuotesServiceTest {
    @Container
    private final GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6")).withExposedPorts(6379);

    private QuotesService sut;

    @BeforeEach
    public void initialize() {
        final var lettuceConnectionFactory = new LettuceConnectionFactory(redis.getHost(), redis.getMappedPort(6379));

        lettuceConnectionFactory.afterPropertiesSet();

        final var redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();

        final var redisKeyValueAdapter = new RedisKeyValueAdapter(redisTemplate);
        final var redisMappingContext = new RedisMappingContext();
        final var redisKeyValueTemplate = new RedisKeyValueTemplate(redisKeyValueAdapter, redisMappingContext);
        final var redisRepositoryFactory = new RedisRepositoryFactory(redisKeyValueTemplate);
        final var quotesRepository = redisRepositoryFactory.getRepository(QuotesRepository.class);

        sut = new QuotesService(quotesRepository);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    public void test(Quote quote) {
        sut.save(quote);

        var savedQuote = sut.get(quote.getId());

        Assertions.assertTrue(savedQuote.isPresent());
        Assertions.assertEquals(quote, savedQuote.get());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> test() {
        return IntStream
            .range(0, 10)
            .mapToObj(Integer::toString)
            .map(test -> Arguments.of(Named.of(test, new Quote(test, test))));
    }
}
