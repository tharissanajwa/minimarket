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
}
