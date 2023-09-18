package com.springboot.minimarket.controllers;

import com.springboot.minimarket.dto.requests.ProductRequest;
import com.springboot.minimarket.dto.responses.ProductResponse;
import com.springboot.minimarket.models.ApiResponse;
import com.springboot.minimarket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait barang
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Metode untuk mengambil semua data barang dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProduct(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Page<ProductResponse> products = productService.getAllProduct(page, limit);
        ApiResponse response = new ApiResponse(productService.getResponseMessage(), products);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data barang berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponse products = productService.getProductByIdResponse(id);
        ApiResponse response = new ApiResponse(productService.getResponseMessage(), products);
        if (products != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat barang baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse products = productService.insertProduct(productRequest);
        ApiResponse response = new ApiResponse(productService.getResponseMessage(), products);
        if (products != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk memperbarui informasi barang dari fungsi yg telah dibuat di service
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        ProductResponse products = productService.updateProduct(id, productRequest);
        ApiResponse response = new ApiResponse(productService.getResponseMessage(), products);
        if (products != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menghapus barang berdasarkan dari fungsi yg telah dibuat di service
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> disableProduct(@PathVariable("id") Long id) {
        boolean valid = productService.deleteProduct(id);
        ApiResponse response = new ApiResponse(productService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
