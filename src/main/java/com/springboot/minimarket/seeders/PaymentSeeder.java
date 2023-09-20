package com.springboot.minimarket.seeders;

import com.springboot.minimarket.models.Member;
import com.springboot.minimarket.models.Payment;
import com.springboot.minimarket.repositories.MemberRepository;
import com.springboot.minimarket.repositories.PaymentRepository;
import com.springboot.minimarket.services.MemberService;
import com.springboot.minimarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentSeeder {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    public void seed() {
        // Daftar pembayaran yang akan disimpan dalam database
        List<Payment> payments = new ArrayList<>(Arrays.asList(
                new Payment(orderService.getOrderById(1L), 106000, 0, 0, 110000, 4000),
                new Payment(orderService.getOrderById(2L), 54500, 0, 0, 55000, 500),
                new Payment(orderService.getOrderById(3L), 61000, 0, 0, 70000, 9000),
                new Payment(orderService.getOrderById(4L), 226000, 0, 0, 250000, 24000),
                new Payment(orderService.getOrderById(7L), 79500, 0, 0, 80000, 500),
                new Payment(orderService.getOrderById(8L), 104500, 0, 0, 110000, 5500),
                new Payment(orderService.getOrderById(9L), 86000, 0, 0, 90000, 4000),
                new Payment(orderService.getOrderById(10L), 87000, 0, 0, 100000, 13000),
                new Payment(orderService.getOrderById(11L), 72000, 0, 0, 80000, 8000),
                new Payment(orderService.getOrderById(12L), 70000, 0, 0, 70000, 0)
        ));

        // Cek apakah database sudah berisi data pembayaran atau tidak
        if (paymentRepository.findAll().isEmpty()) {
            // Jika tidak ada data, maka simpan data pembayaran ke dalam database
            Long memberOrder1And11 = orderService.getOrderById(1L).getMember().getId();
            Long memberOrder2And4 = orderService.getOrderById(2L).getMember().getId();
            Long memberOrder3And8And9 = orderService.getOrderById(3L).getMember().getId();
            Long memberOrder7 = orderService.getOrderById(7L).getMember().getId();
            Long memberOrder10 = orderService.getOrderById(10L).getMember().getId();

            // Add point to member
            Member member1 = memberService.getMemberById(memberOrder1And11);
            member1.addPoints(17800);
            memberRepository.save(member1);

            Member member2 = memberService.getMemberById(memberOrder2And4);
            member2.addPoints(28050);
            memberRepository.save(member2);

            Member member3 = memberService.getMemberById(memberOrder3And8And9);
            member3.addPoints(25150);
            memberRepository.save(member3);

            Member member4 = memberService.getMemberById(memberOrder7);
            member4.addPoints(7950);
            memberRepository.save(member4);

            Member member5 = memberService.getMemberById(memberOrder10);
            member5.addPoints(8700);
            memberRepository.save(member5);

            paymentRepository.saveAll(payments);
        }
    }
}
