package com.springboot.minimarket.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest {

    @JsonProperty("point_used")
    private Integer pointUsed;

    @JsonProperty("total_paid")
    private Integer totalPaid;

    public Integer getPointUsed() {
        return pointUsed;
    }

    public Integer getTotalPaid() {
        return totalPaid;
    }
}
