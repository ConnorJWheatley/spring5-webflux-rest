package guru.springframework.spring5webfluxrest.repositories;

import guru.springframework.spring5webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 15:44
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
