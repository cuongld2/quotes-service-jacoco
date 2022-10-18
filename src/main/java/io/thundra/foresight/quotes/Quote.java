package io.thundra.foresight.quotes;

import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@RedisHash("Quote")
public class Quote {
    private final String id;
    private final String quote;

    public Quote(String id, String quote) {
        this.id = id;
        this.quote = quote;
    }

    public String getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Quote quote1 = (Quote) o;
        return Objects.equals(id, quote1.id) && Objects.equals(quote, quote1.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quote);
    }

    @Override
    public String toString() {
        return "Quote{" +
               "id='" + id + '\'' +
               ", quote='" + quote + '\'' +
               '}';
    }
}
