package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindTop3ProductResponse {
    @JsonProperty("product_name")
    private String name;

    public FindTop3ProductResponse(String name) {
        this.name = name;
    }
}
