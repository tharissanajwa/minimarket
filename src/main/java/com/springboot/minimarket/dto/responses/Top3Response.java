package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Top3Response {
    @JsonProperty("product_name")
    private String name;

    public Top3Response(String name) {
        this.name = name;
    }
}
