package com.springboot.minimarket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment extends BaseModel {

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "point_used")
    private Integer pointUsed = 0;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "total_paid")
    private Integer totalPaid;

    @Column(name = "change")
    private Integer change;

    public Payment() {
    }

    public Payment(Order order, Integer totalAmount, Integer pointUsed, Integer discount, Integer totalPaid, Integer change) {
        this.order = order;
        this.totalAmount = totalAmount;
        this.pointUsed = pointUsed;
        this.discount = discount;
        this.totalPaid = totalPaid;
        this.change = change;
    }

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPointUsed() {
        return pointUsed;
    }

    public void setPointUsed(Integer pointUsed) {
        this.pointUsed = pointUsed;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Integer totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }
}
