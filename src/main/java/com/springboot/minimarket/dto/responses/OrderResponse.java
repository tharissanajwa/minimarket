package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.minimarket.models.Order;
import com.springboot.minimarket.models.OrderDetail;
import com.springboot.minimarket.models.Payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("inv_code")
    private String invCode;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("member_name")
    private String memberName;

    @JsonProperty("order_details")
    private List<OrderDetailResponse> orderDetailsResponse;

    @JsonProperty("total_item")
    private Integer totalItem;

    @JsonProperty("total_amount")
    private Integer totalAmount;

    @JsonProperty("point_obtained")
    private Integer pointObtained;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonProperty("payment")
    private Payment payment;

    @JsonProperty("created_at")
    private Date createdAt;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.invCode = order.getInvCode();
        this.employeeName = order.getEmployee().getName();
        if (order.getMember() == null) {
            this.memberName = null;
        } else {
            this.memberName = order.getMember().getName();
        }
        this.orderDetailsResponse = new ArrayList<>();
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse(orderDetail);
            orderDetailsResponse.add(orderDetailResponse);
        }
        this.totalItem = order.getTotalItem();
        this.totalAmount = order.getTotalAmount();
        this.pointObtained = order.getPointObtained();
        this.isPaid = order.getPaid();
        this.payment = order.getPayment();
        this.createdAt = order.getCreatedAt();
    }
}
