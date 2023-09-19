package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.minimarket.models.OrderDetail;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("total")
    private Integer total;

    public OrderDetailResponse(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.name = orderDetail.getProduct().getName();
        this.price = orderDetail.getPrice();
        this.qty = orderDetail.getQty();
        this.discount = orderDetail.getDiscount();
        this.total = orderDetail.getTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
