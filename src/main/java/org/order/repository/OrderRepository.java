package org.order.repository;

import java.util.Optional;
import java.util.UUID;
import org.order.entity.Order;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Cacheable(value = "orders")
    @Query("SELECT o FROM Order o join fetch o.goods where o.id = :id")
    Optional<Order> findOrderWithGoodsById(UUID id);

}
