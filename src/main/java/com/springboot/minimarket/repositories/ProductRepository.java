package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByDeletedAtIsNullOrderByName(Pageable pageable);
    Optional<Product> findByIdAndDeletedAtIsNull(Long id);
    Optional<Product> findBySkuProductAndDeletedAtIsNull(String skuProduct);
}
