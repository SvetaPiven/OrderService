package org.order.repository;

import java.util.Optional;
import org.order.entity.Good;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GoodRepository extends JpaRepository<Good, Long> {
    @Cacheable(value = "goods")
    Optional<Good> findById(Long id);

}