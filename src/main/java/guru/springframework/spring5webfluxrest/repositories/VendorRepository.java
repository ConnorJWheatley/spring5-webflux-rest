package guru.springframework.spring5webfluxrest.repositories;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @Author: Connor Wheatley
 * @Date: 28/01/2022 16:02
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
