package com.dcb.OrderService.service;

import com.dcb.OrderService.entity.Order;
import com.dcb.OrderService.exception.CustomException;
import com.dcb.OrderService.external.client.PaymentService;
import com.dcb.OrderService.external.client.ProductService;
import com.dcb.OrderService.external.request.PaymentRequest;
import com.dcb.OrderService.external.response.PaymentResponse;
import com.dcb.OrderService.model.OrderRequest;
import com.dcb.OrderService.model.OrderResponse;
import com.dcb.OrderService.external.response.ProductResponse;
import com.dcb.OrderService.repository.OrderRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderSerivceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("placing order request: {}", orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("creating order with status created");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepo.save(order);

        log.info("calling payment serive to complete payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the order status");
            orderStatus = "PLACED";
        }catch (Exception e) {
            log.error("error occured in payment. payment failed");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepo.save(order);

        log.info("Order placed with OrderId: {}", order.getId());
        return order.getId();

    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("get order details for id: {}", orderId);

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found for orderId: " + orderId, "NOT_FOUND", 404));

        log.info("get product details for id: {}", order.getProductId());
        ProductResponse productResponse= null;
        try{
            productResponse = productService.getProductById(order.getProductId()).getBody();
        }catch (Exception ex){
            throw new CustomException("error occured while fetching product details",ex.getMessage(), 500);
        }
        OrderResponse.ProductDetail productDetail = new OrderResponse.ProductDetail();
        assert productResponse != null;
        BeanUtils.copyProperties(productResponse, productDetail);

        log.info("get payment details for id: {}", order.getProductId());
        PaymentResponse paymentResponse = null;
        try{
            paymentResponse = paymentService.getPaymentDetailsByOrderId(orderId).getBody();
        }catch (Exception ex){
            throw new CustomException("error occured while fetching payment details",ex.getMessage(), 500);
        }
        OrderResponse.PaymentDetails paymentDetails = new OrderResponse.PaymentDetails();
        assert paymentResponse != null;
        BeanUtils.copyProperties(paymentResponse, paymentDetails);

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .productDetail(productDetail)
                .paymentDetails(paymentDetails)
                .build();
    }
}
