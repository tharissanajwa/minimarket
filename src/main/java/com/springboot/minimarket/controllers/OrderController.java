package com.springboot.minimarket.controllers;

import com.springboot.minimarket.dto.requests.OrderDetailRequest;
import com.springboot.minimarket.dto.requests.OrderRequest;
import com.springboot.minimarket.dto.responses.OrderDetailResponse;
import com.springboot.minimarket.dto.responses.OrderResponse;
import com.springboot.minimarket.dto.responses.FindProductBoughtTogetherResponse;
import com.springboot.minimarket.dto.responses.FindTop3ProductResponse;
import com.springboot.minimarket.models.ApiResponse;
import com.springboot.minimarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.util.Date;
import java.util.List;

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

    // Metode untuk menghapus penjualan berdasarkan id dari fungsi yg telah dibuat di service
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

    // Metode untuk menghapus order detail berdasarkan id penjualan dari fungsi yg telah dibuat di service
    @DeleteMapping("/{id}/details/{orderDetailId}")
    public ResponseEntity<ApiResponse> disableOrderDetail(@PathVariable("id") Long id, @PathVariable("orderDetailId") Long orderDetailId) {
        boolean valid = orderService.deleteOrderDetail(id, orderDetailId);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk mengambil semua data penjualan berdasarkan rentang waktu tertentu dari fungsi yg telah dibuat di service
    @GetMapping("/dates")
    public ResponseEntity<ApiResponse> getAllOrderDateBetween(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Page<OrderResponse> orders = orderService.getAllOrderDateBetween(page, limit, startDate, endDate);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orders);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil semua data pembelian berdasarkan member dan rentang waktu tertentu dari fungsi yg telah dibuat di service
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse> getAllOrderByMemberAndDateBetween(@PathVariable("memberId") Long memberId, @RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Page<OrderResponse> orders = orderService.getAllOrderByMemberAndDateBetween(page, limit, memberId, startDate, endDate);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orders);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil top 3 product yang sering dibeli
    @GetMapping("/products")
    public ResponseEntity<ApiResponse> getTop3Product() {
        List<FindTop3ProductResponse> orders = orderService.getTop3Product();
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orders);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mendapatkan produk lain yg dibeli bersamaan dengan produk tersebut
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse> getProductBoughtTogetherByProductId(@PathVariable("id") Long productId) {
        List<FindProductBoughtTogetherResponse> orders = orderService.getProductBoughtTogetherByProductId(productId);
        ApiResponse response = new ApiResponse(orderService.getResponseMessage(), orders);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}