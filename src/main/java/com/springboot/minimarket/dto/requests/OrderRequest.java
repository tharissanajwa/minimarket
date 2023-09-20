package com.springboot.minimarket.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("member_id")
    private Long memberId;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
