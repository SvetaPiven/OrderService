package org.order.repository;

import org.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

//    @Transactional
//    @Modifying
//    @Query("update Order o set o.isPaid = true where o.id = :id")
//    int updateStatusById(UUID id);
}
