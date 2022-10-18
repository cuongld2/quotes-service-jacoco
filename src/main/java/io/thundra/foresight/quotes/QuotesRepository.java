package io.thundra.foresight.quotes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotesRepository extends CrudRepository<Quote, String> {
}
