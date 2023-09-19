package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByDeletedAtIsNullOrderByIdDesc(Pageable pageable);
    Optional<Order> findByIdAndDeletedAtIsNull(Long id);
}
