package com.springboot.minimarket.services;

import com.springboot.minimarket.dto.requests.PaymentRequest;
import com.springboot.minimarket.dto.responses.PaymentResponse;
import com.springboot.minimarket.models.Member;
import com.springboot.minimarket.models.Order;
import com.springboot.minimarket.models.Payment;
import com.springboot.minimarket.repositories.OrderRepository;
import com.springboot.minimarket.repositories.PaymentRepository;
import com.springboot.minimarket.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mengambil semua data pembayaran
    public Page<PaymentResponse> getAllPayment(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Payment> result = paymentRepository.findAllByOrderByIdDesc(pageable);
        List<PaymentResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (Payment payment : result.getContent()) {
                PaymentResponse paymentResponse = new PaymentResponse(payment);
                responses.add(paymentResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mengambil data pembayaran berdasarkan id
    public Payment getPaymentById(Long id) {
        Optional<Payment> result = paymentRepository.findById(id);
        if (!result.isPresent()) {
            responseMessage = Utility.message("payment_not_found");
            return null;
        }
        responseMessage = Utility.message("data_displayed");
        return result.get();
    }

    // Metode untuk membuat pembayaran baru
    public PaymentResponse insertPayment(Long orderId, PaymentRequest paymentRequest) {
        if (!isPaymentValid(orderId).isEmpty()) {
            responseMessage = isPaymentValid(orderId);
            return null;
        } else {
            PaymentResponse response = null;
            Payment result = new Payment();
            Order order = orderService.getOrderById(orderId);
            Integer totalAmount = order.getTotalAmount();
            Integer pointUsed = 0;
            if (order.getMember() != null) {
                pointUsed = paymentRequest.getPointUsed();
            }
            Integer totalPaid = paymentRequest.getTotalPaid();
            Integer discount = calculateDiscount(orderId, pointUsed);
            Integer change = calculateChange(totalPaid, totalAmount, discount);

            if (change == null) {
                return null;
            } else {
                result.setOrder(order);
                result.setTotalAmount(totalAmount);
                result.setPointUsed(pointUsed);
                result.setDiscount(discount);
                result.setTotalPaid(totalPaid);
                result.setChange(change);
                paymentRepository.save(result);
                response = new PaymentResponse(result);
                responseMessage = Utility.message("data_added");

                int pointToAdd = order.getPointObtained();
                if (pointUsed == null || pointUsed == 0) {
                    if (order.getMember() != null) {
                        order.getMember().addPoints(pointToAdd);
                    }
                }
                order.setPaid(true);
                orderRepository.save(order);

                return response;
            }
        }
    }

    // Metode untuk menghapus pembayaran
    public boolean deletePayment(Long paymentId) {
        boolean result = false;
        Payment payment = getPaymentById(paymentId);
        if (payment != null) {
            Long orderId = payment.getOrder().getId();
            Order order = orderService.getOrderById(orderId);
            if (order.getMember() != null) {
                Integer point = payment.getPointUsed();
                int pointObtained = order.getPointObtained();
                if (point != null && point != 0) {
                    order.getMember().addPoints(point);
                } else {
                    order.getMember().minusPoints(pointObtained);
                }
            }

            order.setPaid(false);

            orderRepository.save(order);
            paymentRepository.deleteById(paymentId);

            result = true;
            responseMessage = Utility.message("data_deleted");
        }
        return result;
    }

    // Metode untuk validasi pembayaran
    private String isPaymentValid(Long orderId) {
        String result = "";
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            result = Utility.message("order_not_found");
        } else {
            if (order.getPaid()) {
                result = "Payment cannot be made because the order has been paid.";
            }  else if (order.getOrderDetails().isEmpty()) {
                result = "Sorry, order with ID " + orderId + " doesn't have any product yet.";
            }
        }
        return result;
    }

    // Metode untuk menghitung diskon dari point member yang tersedia untuk insert payment
    private Integer calculateDiscount(Long orderId, Integer pointUsed) {
        Order order = orderService.getOrderById(orderId);
        Member member = order.getMember();
        Integer discount = null;

        if (order.getMember() != null && pointUsed != null) {
            if (order.getMember().getPoint() == 0) {
                responseMessage = "Sorry, member " + member.getName() + " don't have any points.";
            } else if (pointUsed > 50000) {
                responseMessage = "Sorry, maximum point to use is 50000.";
            } else if (pointUsed > member.getPoint()) {
                responseMessage = "Sorry, member " + member.getName() + " only have " + member.getPoint() + " points.";
            } else {
                int totalToPointConversion = order.getTotalAmount();

                if (pointUsed > order.getTotalAmount()) {
                    responseMessage = "Sorry, point to use is maxed at " + totalToPointConversion + ".";
                } else {
                    discount = pointUsed;
                    member.minusPoints(pointUsed);
                }
            }
        } else {
            discount = 0;
        }
        return discount;
    }

    // Metode untuk menghitung kembalian dari total yang sudah dibayarkan
    private Integer calculateChange(Integer totalPaid, Integer totalAmount, Integer discount) {
        Integer result = null;

        if (discount != null) {
            int totals = totalAmount - discount;
            if (totalPaid < totals) {
                responseMessage = "Sorry, the total paid must exceed the total amount.";
            } else {
                result = totalPaid - totals;
            }
        }
        return result;
    }
}