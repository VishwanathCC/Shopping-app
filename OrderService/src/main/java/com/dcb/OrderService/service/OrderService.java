package com.dcb.OrderService.service;

import com.dcb.OrderService.model.OrderRequest;
import com.dcb.OrderService.model.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
