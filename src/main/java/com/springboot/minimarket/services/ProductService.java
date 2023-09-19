package com.springboot.minimarket.services;

import com.springboot.minimarket.dto.requests.ProductRequest;
import com.springboot.minimarket.dto.responses.ProductResponse;
import com.springboot.minimarket.models.Product;
import com.springboot.minimarket.repositories.ProductRepository;
import com.springboot.minimarket.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Pesan status untuk memberi informasi kepada pengguna
    private String responseMessage;

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar barang yang belum terhapus melalui repository
    public Page<ProductResponse> getAllProduct(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Product> result = productRepository.findAllByDeletedAtIsNullOrderByName(pageable);
        List<ProductResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (Product product : result.getContent()) {
                ProductResponse productResponse = new ProductResponse(product);
                responses.add(productResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data barang berdasarkan id melalui repository
    public Product getProductById(Long id) {
        Optional<Product> result = productRepository.findByIdAndDeletedAtIsNull(id);
        if (!result.isPresent()) {
            responseMessage = Utility.message("product_not_found");
            return null;
        }
        responseMessage = Utility.message("data_displayed");
        return result.get();
    }

    // Metode untuk mendapatkan data barang melalui response berdasarkan id melalui repository
    public ProductResponse getProductByIdResponse(Long id) {
        ProductResponse response = null;
        Product product = getProductById(id);
        if (product != null) {
            response = new ProductResponse(product);
        }
        return response;
    }

    // Metode untuk menambahkan barang ke dalam data melalui repository
    public ProductResponse insertProduct(ProductRequest productRequest) {
        ProductResponse response = null;
        Product result = new Product();
        String skuProduct = generateSkuProduct();
        String name = Utility.inputTrim(productRequest.getName());
        String description = Utility.inputTrim(productRequest.getDescription());
        Integer price = productRequest.getPrice();
        Integer qty = productRequest.getQty();

        if (!inputValidation(name).isEmpty()) {
            responseMessage = inputValidation(name);
        } else if (!inputValidationInt(price, qty).isEmpty()) {
            responseMessage = inputValidationInt(price, qty);
        } else {
            result.setSkuProduct(skuProduct);
            result.setName(name);
            result.setDescription(description);
            result.setPrice(price);
            result.setQty(qty);
            productRepository.save(result);
            response = new ProductResponse(result);
            responseMessage = Utility.message("data_added");
        }
        return response;
    }

    // Metode untuk memperbarui informasi barang melalui repository
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        ProductResponse response = null;
        Product result = getProductById(id);
        String name = Utility.inputTrim(productRequest.getName());
        String description = Utility.inputTrim(productRequest.getDescription());
        Integer price = productRequest.getPrice();
        Integer qty = productRequest.getQty();

        if (!inputValidation(name).isEmpty()) {
            responseMessage = inputValidation(name);
        } else if (!inputValidationInt(price, qty).isEmpty()) {
            responseMessage = inputValidationInt(price, qty);
        } else if (result != null){
            result.setName(name);
            result.setDescription(description);
            result.setPrice(price);
            result.setQty(qty);
            productRepository.save(result);
            response = new ProductResponse(result);
            responseMessage = Utility.message("data_updated");
        }
        return response;
    }

    // Metode untuk menghapus data barang secara soft delete melalui repository
    public boolean deleteProduct(Long id) {
        boolean result = false;
        Product product = getProductById(id);
        if (product != null) {
            product.setDeletedAt(new Date());
            productRepository.save(product);
            result = true;
            responseMessage = Utility.message("data_deleted");
        }
        return result;
    }

    private String generateSkuProduct() {
        int getProduct = productRepository.findAll().size() + 1;
        return "PRD-"+getProduct;
    }

    // Metode untuk memvalidasi inputan pengguna
    private String inputValidation(String name) {
        String result = "";
        if (Utility.inputContainsNumber(name) == 1) {
            result = "Sorry, product name cannot be blank.";
        }
        if (Utility.inputContainsNumber(name) == 2) {
            result = "Sorry, product name can only filled by letters and numbers";
        }
        return result;
    }

    // Metode untuk memvalidasi inputan pengguna untuk harga (price)
    private String inputValidationInt(Integer price, Integer qty) {
        String result = "";
        if (price < 0) {
            result = "Sorry, price must be more than 0.";
        }
        if (qty < 0) {
            result = "Sorry, qty must be more than 0.";
        }
        return result;
    }
}
