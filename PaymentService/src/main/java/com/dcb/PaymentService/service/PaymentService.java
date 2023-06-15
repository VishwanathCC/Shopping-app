package com.dcb.PaymentService.service;

import com.dcb.PaymentService.model.PaymentRequest;
import com.dcb.PaymentService.model.PaymentResponse;

public interface PaymentService {

    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
