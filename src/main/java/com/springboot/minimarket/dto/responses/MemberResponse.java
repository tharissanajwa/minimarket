package com.springboot.minimarket.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.minimarket.models.Member;

public class MemberResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("point")
    private Integer point;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.point = member.getPoint();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
