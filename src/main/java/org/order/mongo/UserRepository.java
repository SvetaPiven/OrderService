package org.order.mongo;

import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  @Cacheable("users")
  Optional<User> findByUserId(String userId);

}
