package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindProductBoughtTogetherResponse {
    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_bought_together_1")
    private String productBoughtTogether1;

    @JsonProperty("product_bought_together_2")
    private String productBoughtTogether2;
    public FindProductBoughtTogetherResponse(String productName, String productBoughtTogether1, String productBoughtTogether2) {
        this.productName = productName;
        this.productBoughtTogether1 = productBoughtTogether1;
        this.productBoughtTogether2 = productBoughtTogether2;
    }
}
