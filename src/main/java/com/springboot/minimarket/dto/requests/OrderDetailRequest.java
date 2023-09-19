package com.springboot.minimarket.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDetailRequest {

    @JsonProperty("sku_product")
    private String skuProduct;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("total")
    private Integer total;

    public String getSkuProduct() {
        return skuProduct;
    }

    public void setSkuProduct(String skuProduct) {
        this.skuProduct = skuProduct;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
