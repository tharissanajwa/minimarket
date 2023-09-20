package com.springboot.minimarket.seeders;

import com.springboot.minimarket.models.Member;
import com.springboot.minimarket.models.Payment;
import com.springboot.minimarket.repositories.MemberRepository;
import com.springboot.minimarket.repositories.PaymentRepository;
import com.springboot.minimarket.services.MemberService;
import com.springboot.minimarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PaymentSeeder {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @PostConstruct
    public void seed() {
        // Daftar pembayaran yang akan disimpan dalam database
        List<Payment> payments = new ArrayList<>(Arrays.asList(
                new Payment(orderService.getOrderById(1L), 106000, 0, 0, 110000, 4000),
                new Payment(orderService.getOrderById(2L), 54500, 0, 0, 55000, 500),
                new Payment(orderService.getOrderById(4L), 226000, 0, 0, 250000, 24000),
                new Payment(orderService.getOrderById(12L), 70000, 0, 0, 70000, 0)
        ));

        // Cek apakah database sudah berisi data pembayaran atau tidak
        if (paymentRepository.findAll().isEmpty()) {
            // Jika tidak ada data, maka simpan data pembayaran ke dalam database
            Long memberOrder1 = orderService.getOrderById(1L).getMember().getId();
            Long memberOrder2And4 = orderService.getOrderById(2L).getMember().getId();

            Member member1 = memberService.getMemberById(memberOrder1);
            member1.addPoints(10600);
            memberRepository.save(member1);
            Member member2 = memberService.getMemberById(memberOrder2And4);
            member2.addPoints(28050);
            memberRepository.save(member2);
            paymentRepository.saveAll(payments);
        }
    }
}
