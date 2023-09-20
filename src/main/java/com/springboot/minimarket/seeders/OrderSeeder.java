package com.springboot.minimarket.seeders;

import com.springboot.minimarket.models.Order;
import com.springboot.minimarket.repositories.OrderRepository;
import com.springboot.minimarket.services.EmployeeService;
import com.springboot.minimarket.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OrderSeeder {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MemberService memberService;

    public void seed() {
        // Daftar penjualan yang akan disimpan dalam database
        List<Order> orders = new ArrayList<>(Arrays.asList(
                new Order("ORD-1", employeeService.getEmployeeById(1L), memberService.getMemberById(1L), 8, 106000, 10600, true, new Date(1694563200000L)), // Tanggal 2023-09-13
                new Order("ORD-2", employeeService.getEmployeeById(2L), memberService.getMemberById(2L), 6, 54500, 5450, true, new Date(1694649600000L)), // Tanggal 2023-09-14
                new Order("ORD-3", employeeService.getEmployeeById(2L), memberService.getMemberById(3L), 6, 61000, 6100, true, new Date(1694736000000L)), // Tanggal 2023-09-15
                new Order("ORD-4", employeeService.getEmployeeById(4L), memberService.getMemberById(2L), 7, 226000, 22600, true, new Date(1694563200000L)), // Tanggal 2023-09-13
                new Order("ORD-5", employeeService.getEmployeeById(5L), memberService.getMemberById(4L), 7, 128000, 12800, false, new Date(1694822400000L)), // Tanggal 2023-09-16
                new Order("ORD-6", employeeService.getEmployeeById(5L), null, 7, 106000, 0, false, new Date(1694649600000L)), // Tanggal 2023-09-14
                new Order("ORD-7", employeeService.getEmployeeById(5L), memberService.getMemberById(5L), 6, 79500, 7950, true, new Date(1694736000000L)), // Tanggal 2023-09-15
                new Order("ORD-8", employeeService.getEmployeeById(6L), memberService.getMemberById(3L), 6, 104500, 10450, true, new Date(1694563200000L)), // Tanggal 2023-09-13
                new Order("ORD-9", employeeService.getEmployeeById(8L), memberService.getMemberById(3L), 6, 86000, 8600, true, new Date(1694908800000L)), // Tanggal 2023-09-17
                new Order("ORD-10", employeeService.getEmployeeById(9L), memberService.getMemberById(9L), 6, 87000, 8700, true, new Date(1694995200000L)), // Tanggal 2023-09-18
                new Order("ORD-11", employeeService.getEmployeeById(10L), memberService.getMemberById(1L), 6, 72000, 7200, true, new Date(1695056400000L)), // Tanggal 2023-09-19
                new Order("ORD-12", employeeService.getEmployeeById(7L), null, 6, 70000, 0, true, new Date(1695056400000L)) // Tanggal 2023-09-19
        ));

        // Cek apakah database sudah berisi data penjualan atau tidak
        if (orderRepository.findAll().isEmpty()) {
            // Jika tidak ada data, maka simpan data penjualan ke dalam database
            orderRepository.saveAll(orders);
        }
    }
}
