package com.main.services.implementation;

import com.main.dto.PaymentResponse;
import com.main.entity.Payment;
import com.main.enums.PaymentStatus;
import com.main.exceptions.ResourceNotFoundException;
import com.main.repository.PaymentRepository;
import com.main.services.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${razorpay.api-key}")
    private String apiKey;
    @Value("${razorpay.secret-key}")
    private String secretKey;

    @Override
    public PaymentResponse createOrder(Long orderId, BigDecimal amount) throws RazorpayException {
        logger.info("Starting order creation for Order ID: {} with amount: {}", orderId, amount);
        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, secretKey);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount.multiply(new BigDecimal(100)));
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_" + orderId);

            logger.debug("Sending order creation request to Razorpay: {}", orderRequest);
            Order razorpayOrder = razorpayClient.orders.create(orderRequest);

            logger.info("Razorpay order created successfully: {}", razorpayOrder);

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setAmount(amount);
            payment.setPaymentMethod("COD");
            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setRazorPayOrderId(razorpayOrder.get("id"));

            Payment savedPayment = paymentRepository.save(payment);
            logger.info("Payment order saved to the database for Order ID: {}", orderId);

            return modelMapper.map(savedPayment, PaymentResponse.class);
        } catch (RazorpayException e) {
            logger.error("Error creating Razorpay order for Order ID: {}: {}", orderId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during order creation for Order ID: {}: {}", orderId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void webhookPayload(String payload) {
        logger.info("Processing webhook payload: {}", payload);
        try {
            JSONObject json = new JSONObject(payload);
            JSONObject payment = json.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity");

            String event = json.getString("event");
            String paymentId = payment.getString("id");
            String paymentMethod = payment.getString("method");
            String paymentStatus = payment.getString("status");
            String orderId = payment.optString("order_id", null);

            logger.info("Webhook Event: {}, Payment ID: {}, Order ID: {}", event, paymentId, orderId);

            if ("order.paid".equalsIgnoreCase(event) && orderId != null) {
                updateOrder(orderId, PaymentStatus.COMPLETED, paymentMethod, paymentId);
            } else if ("payment.failed".equalsIgnoreCase(event) && orderId != null) {
                updateOrder(orderId, PaymentStatus.FAILED, paymentMethod, paymentId);
            } else {
                logger.warn("Unhandled webhook event or missing order ID: {}", event);
            }
        } catch (Exception e) {
            logger.error("Error processing webhook payload: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void updateOrder(String razorPayOrderId, PaymentStatus paymentStatus, String paymentMethod, String razorPayPaymentId) {
        logger.info("Updating payment order. Razorpay Order ID: {}, Payment Status: {}", razorPayOrderId, paymentStatus);
        try {
            Payment payment = paymentRepository.findByRazorPayOrderId(razorPayOrderId)
                    .orElseThrow(() -> {
                        logger.error("Payment order not found for Razorpay Order ID: {}", razorPayOrderId);
                        return new ResourceNotFoundException("Order not found with given order id: " + razorPayOrderId);
                    });

            payment.setPaymentStatus(paymentStatus);
            payment.setPaymentMethod(paymentMethod);
            payment.setRazorPayPaymentId(razorPayPaymentId);

            Payment updatedPayment = paymentRepository.save(payment);
            PaymentResponse paymentResponse = modelMapper.map(updatedPayment, PaymentResponse.class);

            logger.info("Payment order updated successfully: {}", paymentResponse);
        } catch (ResourceNotFoundException e) {
            logger.error("Error updating payment order: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error updating payment order: {}", e.getMessage(), e);
            throw e;
        }
    }
}
