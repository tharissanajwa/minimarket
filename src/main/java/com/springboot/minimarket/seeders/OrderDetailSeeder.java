package com.springboot.minimarket.seeders;

import com.springboot.minimarket.models.OrderDetail;
import com.springboot.minimarket.repositories.OrderDetailRepository;
import com.springboot.minimarket.services.OrderService;
import com.springboot.minimarket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OrderDetailSeeder {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void seed() {
        // Daftar order detail yang akan disimpan dalam database
        List<OrderDetail> orderDetails = new ArrayList<>(Arrays.asList(
                // Order detail untuk id order 1
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(1L), 2, 5000, 0, 10000),
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(2L), 2, 4500, 500, 8000),
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(6L), 1, 15000, 0, 15000),
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(4L), 3, 7000, 1000, 18000),
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(8L), 1, 9000, 0, 9000),
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(10L), 5, 3500, 500, 15000),
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(12L), 10, 3000, 1000, 20000),
                new OrderDetail(orderService.getOrderById(1L), productService.getProductById(14L), 1, 11000, 0, 11000),

                // Order detail untuk id order 2
                new OrderDetail(orderService.getOrderById(2L), productService.getProductById(1L), 2, 5000, 0, 10000),
                new OrderDetail(orderService.getOrderById(2L), productService.getProductById(2L), 2, 4500, 500, 8000),
                new OrderDetail(orderService.getOrderById(2L), productService.getProductById(6L), 1, 15000, 0, 15000),
                new OrderDetail(orderService.getOrderById(2L), productService.getProductById(11L), 3, 3200, 200, 9000),
                new OrderDetail(orderService.getOrderById(2L), productService.getProductById(3L), 1, 5500, 0, 5500),
                new OrderDetail(orderService.getOrderById(2L), productService.getProductById(10L), 2, 3500, 0, 7000),

                // Order detail untuk id order 3
                new OrderDetail(orderService.getOrderById(3L), productService.getProductById(1L), 2, 5000, 0, 10000),
                new OrderDetail(orderService.getOrderById(3L), productService.getProductById(2L), 3, 4500, 500, 12000),
                new OrderDetail(orderService.getOrderById(3L), productService.getProductById(6L), 1, 15000, 1000, 14000),
                new OrderDetail(orderService.getOrderById(3L), productService.getProductById(15L), 3, 2500, 0, 7500),
                new OrderDetail(orderService.getOrderById(3L), productService.getProductById(9L), 1, 6500, 0, 6500),
                new OrderDetail(orderService.getOrderById(3L), productService.getProductById(3L), 2, 5500, 0, 11000),

                // Order detail untuk id order 4
                new OrderDetail(orderService.getOrderById(4L), productService.getProductById(1L), 2, 5000, 0, 10000),
                new OrderDetail(orderService.getOrderById(4L), productService.getProductById(2L), 3, 4500, 500, 12000),
                new OrderDetail(orderService.getOrderById(4L), productService.getProductById(6L), 1, 15000, 1000, 14000),
                new OrderDetail(orderService.getOrderById(4L), productService.getProductById(7L), 3, 55000, 5000, 150000),
                new OrderDetail(orderService.getOrderById(4L), productService.getProductById(5L), 4, 5000, 0, 20000),
                new OrderDetail(orderService.getOrderById(4L), productService.getProductById(4L), 1, 7000, 0, 7000),
                new OrderDetail(orderService.getOrderById(4L), productService.getProductById(13L), 2, 6500, 0, 13000),

                // Order detail untuk id order 6
                new OrderDetail(orderService.getOrderById(6L), productService.getProductById(1L), 2, 5000, 0, 10000),
                new OrderDetail(orderService.getOrderById(6L), productService.getProductById(2L), 3, 4500, 500, 12000),
                new OrderDetail(orderService.getOrderById(6L), productService.getProductById(6L), 1, 15000, 1000, 14000),
                new OrderDetail(orderService.getOrderById(6L), productService.getProductById(8L), 3, 9000, 1000, 24000),
                new OrderDetail(orderService.getOrderById(6L), productService.getProductById(6L), 2, 15000, 0, 30000),
                new OrderDetail(orderService.getOrderById(6L), productService.getProductById(5L), 1, 5000, 0, 5000),
                new OrderDetail(orderService.getOrderById(6L), productService.getProductById(14L), 1, 11000, 0, 11000),

                // Order detail untuk id order 12
                new OrderDetail(orderService.getOrderById(12L), productService.getProductById(1L), 2, 5000, 0, 10000),
                new OrderDetail(orderService.getOrderById(12L), productService.getProductById(2L), 3, 4500, 500, 12000),
                new OrderDetail(orderService.getOrderById(12L), productService.getProductById(6L), 1, 15000, 1000, 14000),
                new OrderDetail(orderService.getOrderById(12L), productService.getProductById(13L), 3, 6500, 500, 18000),
                new OrderDetail(orderService.getOrderById(12L), productService.getProductById(3L), 2, 5500, 0, 11000),
                new OrderDetail(orderService.getOrderById(12L), productService.getProductById(5L), 1, 5000, 0, 5000)
        ));

        // Cek apakah database sudah berisi data order detail atau tidak
        if (orderDetailRepository.findAll().isEmpty()) {
            // Jika tidak ada data, maka simpan data order detail ke dalam database
            if (orderService.getOrderById(1L) != null) {
                orderDetailRepository.saveAll(orderDetails);
            }
        }
    }
}
