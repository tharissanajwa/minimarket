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
    @Query(nativeQuery = true, value = "SELECT c.product, c.bought_with, c.bought_with2\n" +
            "FROM (\n" +
            "  SELECT a.product_id as product, b.product_id as bought_with, od.product_id as bought_with2\n" +
            "  FROM order_details a\n" +
            "  INNER JOIN order_details b\n" +
            "  ON a.order_id = b.order_id AND a.product_id != b.product_id \n" +
            "  JOIN order_details od ON a.order_id = od.order_id AND a.product_id != od.product_id \n" +
            "  AND od.product_id != b.product_id) c \n" +
            "WHERE product = ?1 \n" +
            "GROUP BY c.product, c.bought_with, c.bought_with2\n" +
            "ORDER BY COUNT(*) DESC LIMIT 1")
    String getProductBoughtTogetherByProductId(Long productId);

}
