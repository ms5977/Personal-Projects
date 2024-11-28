package com.main.services;

import com.main.dto.PaymentResponse;
import com.main.enums.PaymentStatus;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentResponse createOrder(Long orderId, BigDecimal amount) throws RazorpayException;
    void webhookPayload(String payload);
    void updateOrder(String razorPayOrderId, PaymentStatus paymentStatus, String paymentMethod, String paymentOrderId);
}
