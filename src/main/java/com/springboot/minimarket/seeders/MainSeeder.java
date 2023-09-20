package com.springboot.minimarket.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MainSeeder {
    @Autowired
    private EmployeeSeeder employeeSeeder;
    @Autowired
    private MemberSeeder memberSeeder;
    @Autowired
    private OrderDetailSeeder orderDetailSeeder;
    @Autowired
    private OrderSeeder orderSeeder;
    @Autowired
    private PaymentSeeder paymentSeeder;
    @Autowired
    private ProductSeeder productSeeder;

    @PostConstruct
    public void seeders() {
        employeeSeeder.seed();
        memberSeeder.seed();
        productSeeder.seed();
        orderSeeder.seed();
        orderDetailSeeder.seed();
        paymentSeeder.seed();
    }
}
