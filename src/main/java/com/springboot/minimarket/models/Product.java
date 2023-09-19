package com.springboot.minimarket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseModel{

    @Column(name = "sku_prod")
    private String skuProduct;

    private String name;

    private Integer price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer qty = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    public Product() {
    }

    public String getSkuProduct() {
        return skuProduct;
    }

    public void setSkuProduct(String skuProduct) {
        this.skuProduct = skuProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
