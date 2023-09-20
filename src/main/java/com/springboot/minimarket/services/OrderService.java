package com.springboot.minimarket.services;

import com.springboot.minimarket.dto.requests.OrderDetailRequest;
import com.springboot.minimarket.dto.requests.OrderRequest;
import com.springboot.minimarket.dto.responses.OrderDetailResponse;
import com.springboot.minimarket.dto.responses.OrderResponse;
import com.springboot.minimarket.models.Employee;
import com.springboot.minimarket.models.Member;
import com.springboot.minimarket.models.Order;
import com.springboot.minimarket.models.OrderDetail;
import com.springboot.minimarket.models.Product;
import com.springboot.minimarket.repositories.OrderDetailRepository;
import com.springboot.minimarket.repositories.OrderRepository;
import com.springboot.minimarket.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProductService productService;

    // Pesan status untuk memberi informasi kepada pengguna
    private String responseMessage;

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar penjualan yang belum terhapus melalui repository
    public Page<OrderResponse> getAllOrder(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Order> result = orderRepository.findAllByDeletedAtIsNullOrderByIdDesc(pageable);
        List<OrderResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (Order order : result.getContent()) {
                OrderResponse orderResponse = new OrderResponse(order);
                responses.add(orderResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data penjualan berdasarkan id melalui repository
    public Order getOrderById(Long id) {
        Optional<Order> result = orderRepository.findByIdAndDeletedAtIsNull(id);
        if (!result.isPresent()) {
            responseMessage = Utility.message("order_not_found");
            return null;
        }
        responseMessage = Utility.message("data_displayed");
        return result.get();
    }

    // Metode untuk mendapatkan data penjualan melalui response berdasarkan id melalui repository
    public OrderResponse getOrderByIdResponse(Long id) {
        OrderResponse response = null;
        Order order = getOrderById(id);
        if (order != null) {
            response = new OrderResponse(order);
        }
        return response;
    }

    // Metode untuk menambahkan penjualan ke dalam data melalui repository
    public OrderResponse insertOrder(OrderRequest orderRequest) {
        OrderResponse response = null;
        Order result = new Order();
        String invCode = generateInvCode();
        Long employeeId = orderRequest.getEmployeeId();
        Long memberId = orderRequest.getMemberId();

        if (!inputValidation(employeeId, memberId).isEmpty()) {
            responseMessage = inputValidation(employeeId, memberId);
        } else {
            Member member = memberService.getMemberById(memberId);
            Employee employee = employeeService.getEmployeeById(employeeId);

            result.setInvCode(invCode);
            result.setEmployee(employee);
            result.setMember(member);

            orderRepository.save(result);
            response = new OrderResponse(result);
            responseMessage = Utility.message("data_added");
        }
        return response;
    }

    // Metode untuk menghapus data penjualan secara soft delete melalui repository
    public boolean deleteOrder(Long id) {
        boolean result = false;
        Order order = getOrderById(id);
        if (validateDeleteOrder(order).isEmpty()) {
            order.setDeletedAt(new Date());
            orderRepository.save(order);
            result = true;
            responseMessage = Utility.message("data_deleted");
        } else {
            responseMessage = validateDeleteOrder(order);
        }
        return result;
    }

    // Metode untuk menambahkan product(detail order) kedalam order berdasarkan id yg diinputkan
    public OrderDetailResponse addProductToOrder(Long orderId, OrderDetailRequest orderDetailRequest) {
        OrderDetailResponse response = null;
        OrderDetail result = new OrderDetail();
        Order order = getOrderById(orderId);
        String skuProduct = orderDetailRequest.getSkuProduct();
        Integer qty = orderDetailRequest.getQty();
        Integer discount = orderDetailRequest.getDiscount();

        if (validateOrderDetailData(orderId, skuProduct, qty, discount).isEmpty()) {
            Product product = productService.getProductBySkuProduct(skuProduct);
            int price = product.getPrice();
            int total = (price - discount) * qty;
            result.setOrder(order);
            result.setProduct(product);
            result.setPrice(price);
            result.setQty(qty);
            result.setDiscount(discount);
            result.setTotal(total);
            int currentQty = product.getQty() - qty;
            product.setQty(currentQty);
            result.setProduct(product);

            orderDetailRepository.save(result);
            response = new OrderDetailResponse(result);

            int totalItem = order.getOrderDetails().size();
            order.setTotalItem(totalItem);
            int totalAmount = accumulateTotalAmount(order);
            order.setTotalAmount(totalAmount);
            if (order.getMember() != null) {
                int pointObtained = totalAmount / 10;
                order.setPointObtained(pointObtained);
            }
            responseMessage = Utility.message("data_added");
            orderRepository.save(order);
        } else {
            responseMessage = validateOrderDetailData(orderId, skuProduct, qty, discount);
        }

        return response;
    }

    // Metode untuk menghapus data order detail secara hard delete melalui repository
    public boolean deleteOrderDetail(Long id, Long detailOrderId) {
        boolean result = false;
        Order order = getOrderById(id);
        if (validateDeleteOrderDetail(order, detailOrderId).isEmpty()) {
            // Mengembalikan quantity product ke posisi sebelumnya
            OrderDetail orderDetail = orderDetailRepository.findByIdAndOrderId(detailOrderId, id).get();
            Long productId = orderDetail.getProduct().getId();
            Product product = productService.getProductById(productId);
            int currentQty = product.getQty() + orderDetail.getQty();
            product.setQty(currentQty);
            orderDetail.setProduct(product);
            orderDetailRepository.save(orderDetail);
            orderDetailRepository.deleteById(detailOrderId); // Menghapus detail order secara permanen
            // Mengembalikan total item, total amount and point obtained ke posisi sebelumnya
            int totalItem = order.getOrderDetails().size();
            order.setTotalItem(totalItem);
            int totalAmount = accumulateTotalAmount(order);
            order.setTotalAmount(totalAmount);
            if (order.getMember() != null) {
                int pointObtained = totalAmount / 10;
                order.setPointObtained(pointObtained);
            }
            orderRepository.save(order);
            result = true;
            responseMessage = Utility.message("data_deleted");
        } else {
            responseMessage = validateDeleteOrderDetail(order, detailOrderId);
        }
        return result;
    }

    // Metode untuk mendapatkan total penjualan berdasarkan rentang waktu tertentu
    public Page<OrderResponse> getAllOrderDateBetween(int page, int limit, Date startDate, Date endDate) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Order> result = orderRepository.findByOrderDateBetweenAndIsPaidIsTrueAndDeletedAtIsNullOrderByOrderDateDesc(startDate, endDate, pageable);
        List<OrderResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            int totalAmount = 0;
            int netProfit = 0;
            for (Order order : result.getContent()) {
                OrderResponse orderResponse = new OrderResponse(order);
                responses.add(orderResponse);
                totalAmount += order.getTotalAmount();
                netProfit += (order.getTotalAmount() - order.getPayment().getDiscount());
            }
            responseMessage = "Total sales from " + startDate + " to " + endDate + " are Rp. " + totalAmount + ". With net profit in Rp. " + netProfit + ".";
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan total pembelian berdasarkan pelanggan dan rentang waktu tertentu
    public Page<OrderResponse> getAllOrderByMemberAndDateBetween(int page, int limit, Long memberId, Date startDate, Date endDate) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Order> result = orderRepository.findByMemberIdAndOrderDateBetweenAndIsPaidIsTrueAndDeletedAtIsNullOrderByOrderDateDesc(memberId, startDate, endDate, pageable);
        List<OrderResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            int totalAmount = 0;
            String memberName = "";
            for (Order order : result.getContent()) {
                OrderResponse orderResponse = new OrderResponse(order);
                responses.add(orderResponse);
                memberName = order.getMember().getName();
                totalAmount += order.getTotalAmount();
            }
            responseMessage = "Total ordered " + memberName + " from " + startDate + " to " + endDate + " are Rp. " + totalAmount + ".";
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    private String generateInvCode() {
        int getOrder = orderRepository.findAll().size() + 1;
        return "ORD-"+getOrder;
    }

    // Metode untuk menghitung total pesanan yang harus dibayarkan
    private int accumulateTotalAmount(Order order) {
        int total = 0;
        for (int indeks = 0; indeks < order.getOrderDetails().size(); indeks++) {
            OrderDetail detail = order.getOrderDetails().get(indeks);
            total += detail.getTotal();
        }
        return total;
    }

    // Metode untuk memvalidasi inputan pengguna
    private String inputValidation(Long employeeId, Long memberId) {
        String result = "";
        if (memberId != null && memberService.getMemberById(memberId) == null) {
            result = "Sorry, the member is not found";
        }
        if (employeeId == null || employeeService.getEmployeeById(employeeId) == null) {
            result = "Sorry, the employee is not found";
        }
        return result;
    }


    // Metode untuk validasi delete order
    private String validateDeleteOrder(Order order) {
        String message = "";
        if (order == null) {
            message = Utility.message("order_not_found");
        } else if (order.getPaid()) {
            message = "Sorry, order can't be deleted because order has been paid.";
        } else if (!order.getOrderDetails().isEmpty()) {
            message = "Sorry, can't remove your order because there is products in this order.";
        }
        return message;
    }

    // Metode untuk memvalidasi tambah product(order detail) ke order
    private String validateOrderDetailData(Long orderId, String skuProduct, Integer qty, Integer discount) {
        String result = "";
        Order order = getOrderById(orderId);
        Product product = productService.getProductBySkuProduct(skuProduct);

        if (order == null) {
            result = "Sorry, order with ID " + orderId + " doesn't exist.";
        } else if (product == null) {
            result = "Sorry, product with SKU " + skuProduct + " doesn't exist.";
        } else if (discount == null) {
            result = "Sorry, discount cannot be blank.";
        } else if (qty == null) {
            result = "Sorry, quantity cannot be blank.";
        } else if (qty < 1) {
            result = "Sorry, quantity must be positive number.";
        } else if (product.getQty() < qty) {
            result = "Sorry, the quantity of product is insufficient.";
        } else if (order.getPaid()) {
            result = "Sorry, order with ID " + order.getId() + " has been paid.";
        }

        return result;
    }

    // Metode untuk validasi delete detail order
    private String validateDeleteOrderDetail(Order order, Long detailOrderId) {
        String message = "";
        Optional<OrderDetail> orderDetail = orderDetailRepository.findByIdAndOrderId(detailOrderId, order.getId());

        if (order == null) {
            message = Utility.message("order_not_found");
        } else if (order.getPaid()) {
            message = "Sorry, order detail can't be deleted because order has been paid.";
        } else if (!orderDetail.isPresent()) {
            message = "Sorry, order detail can't be deleted because order detail is not found in this order.";
        }
        return message;
    }
}
