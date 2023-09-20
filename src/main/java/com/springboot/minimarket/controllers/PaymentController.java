package com.springboot.minimarket.controllers;

import com.springboot.minimarket.dto.requests.PaymentRequest;
import com.springboot.minimarket.dto.responses.PaymentResponse;
import com.springboot.minimarket.models.ApiResponse;
import com.springboot.minimarket.services.PaymentService;
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

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait pembayaran
@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // Metode untuk mengambil semua data pembayaran dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPayment(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Page<PaymentResponse> payments = paymentService.getAllPayment(page, limit);
        ApiResponse response = new ApiResponse(paymentService.getResponseMessage(), payments);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk membuat pembayaran baru dari fungsi yg telah dibuat di service
    @PostMapping("/orders/{id}")
    public ResponseEntity<ApiResponse> insertPayment(@PathVariable("id") Long orderId, @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse payments = paymentService.insertPayment(orderId, paymentRequest);
        ApiResponse response = new ApiResponse(paymentService.getResponseMessage(), payments);
        if (payments != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menghapus pembayaran berdasarkan dari fungsi yg telah dibuat di service
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> disablePayment(@PathVariable("id") Long id) {
        boolean valid = paymentService.deletePayment(id);
        ApiResponse response = new ApiResponse(paymentService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
