package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.minimarket.models.Employee;

public class EmployeeResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.address = employee.getAddress();
        this.phoneNumber = employee.getPhoneNumber();
    }
}
