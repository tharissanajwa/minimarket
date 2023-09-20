package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findByIdAndOrderId(Long id, Long orderId);
    @Query(nativeQuery = true, value = "select p.\"name\" as name\n" +
            "from order_details as od join products as p on od.product_id=p.id\n" +
            "group by name order by count(p.\"name\") desc limit 3")
    List<String> getTop3Product();
}
