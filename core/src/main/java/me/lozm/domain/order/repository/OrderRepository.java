package me.lozm.domain.order.repository;

import me.lozm.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(String userId);

    Optional<Order> findByOrderId(String orderId);

}
