package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findAllByOrderByIdDesc(Pageable pageable);
}
