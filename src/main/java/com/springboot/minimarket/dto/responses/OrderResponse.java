package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.minimarket.models.Order;
import com.springboot.minimarket.models.OrderDetail;
import com.springboot.minimarket.models.Payment;

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
    private List<OrderDetail> orderDetails;

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
        this.orderDetails = order.getOrderDetails();
        this.totalItem = order.getTotalItem();
        this.totalAmount = order.getTotalAmount();
        this.pointObtained = order.getPointObtained();
        this.isPaid = order.getPaid();
        this.payment = order.getPayment();
        this.createdAt = order.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPointObtained() {
        return pointObtained;
    }

    public void setPointObtained(Integer pointObtained) {
        this.pointObtained = pointObtained;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
