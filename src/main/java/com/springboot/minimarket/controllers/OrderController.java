package com.springboot.minimarket.controllers;

import com.springboot.minimarket.dto.requests.OrderDetailRequest;
import com.springboot.minimarket.dto.requests.OrderRequest;
import com.springboot.minimarket.dto.responses.OrderDetailResponse;
import com.springboot.minimarket.dto.responses.OrderResponse;
import com.springboot.minimarket.models.ApiResponse;
import com.springboot.minimarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait penjualan
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // Metode untuk mengambil semua data penjualan dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrder(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Page<OrderResponse> orders = orderService.getAllOrder(page, limit);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orders);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data penjualan berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("id") Long id) {
        OrderResponse orders = orderService.getOrderByIdResponse(id);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orders);
        if (orders != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat penjualan baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orders = orderService.insertOrder(orderRequest);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orders);
        if (orders != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menghapus penjualan berdasarkan dari fungsi yg telah dibuat di service
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> disableOrder(@PathVariable("id") Long id) {
        boolean valid = orderService.deleteOrder(id);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menambahkan product(detail order) kedalam penjualan berdasarkan id yg diinputkan
    @PostMapping("/{id}/details")
    public ResponseEntity<ApiResponse> addDetailToOrder(@PathVariable("id") Long id, @RequestBody OrderDetailRequest orderDetailRequest) {
        OrderDetailResponse orderDetails = orderService.addProductToOrder(id, orderDetailRequest);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orderDetails);
        if (orderDetails != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}