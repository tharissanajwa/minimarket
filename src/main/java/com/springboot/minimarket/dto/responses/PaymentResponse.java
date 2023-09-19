package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.minimarket.models.Payment;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("total_amount")
    private Integer totalAmount;

    @JsonProperty("point_used")
    private Integer pointUsed;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("total_paid")
    private Integer totalPaid;

    @JsonProperty("change")
    private Integer change;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.totalAmount = payment.getTotalAmount();
        this.pointUsed = payment.getPointUsed();
        this.discount = payment.getDiscount();
        this.totalPaid = payment.getTotalPaid();
        this.change = payment.getChange();
    }
}
