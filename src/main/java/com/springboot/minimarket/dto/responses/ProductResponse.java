package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.minimarket.models.Product;

public class ProductResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("sku_prod")
    private String skuProduct;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("qty")
    private Integer qty;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.skuProduct = product.getSkuProduct();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.qty = product.getQty();
    }
}
