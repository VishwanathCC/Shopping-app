package com.dcb.PaymentService.service;

import com.dcb.PaymentService.entity.TransactionDetails;
import com.dcb.PaymentService.model.PaymentMode;
import com.dcb.PaymentService.model.PaymentRequest;
import com.dcb.PaymentService.model.PaymentResponse;
import com.dcb.PaymentService.repository.TransactionDetailRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private TransactionDetailRepo transactionDetailRepo;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .paymentDate(Instant.now())
                .status("SUCCESS")
                .amount(paymentRequest.getAmount())
                .build();
        transactionDetailRepo.save(transactionDetails);

        log.info("Transaction completed with id: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Get Payment Details: {}", orderId);
        TransactionDetails transactionDetails = transactionDetailRepo.findByOrderId(orderId);
        log.info("Transaction Details: {}", transactionDetails);
        return PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .status(transactionDetails.getStatus())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .amount(transactionDetails.getAmount())
                .paymentDate(transactionDetails.getPaymentDate())
                .build();
    }
}