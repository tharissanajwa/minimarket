package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findByIdAndOrderId(Long id, Long orderId);
}
