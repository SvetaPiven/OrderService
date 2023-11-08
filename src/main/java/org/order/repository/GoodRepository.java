package org.order.repository;

import org.order.entity.Good;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GoodRepository extends JpaRepository<Good, Long> {
    @Cacheable(value = "goods")
    Optional<Good> findById(Long id);

}