package com.main.controller;

import com.main.dto.PaymentResponse;
import com.main.services.PaymentService;
import com.razorpay.RazorpayException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Operation(
            summary = "Create a Payment Order",
            description = "Creates a payment order for a specific order ID with the specified amount."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order or amount"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/createOrder/order/{orderId}/amount/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponse> createOrder(
            @Parameter(description = "The unique ID of the order") @PathVariable Long orderId,
            @Parameter(description = "The amount to be charged for the order") @PathVariable BigDecimal amount) throws RazorpayException {
        logger.info("Starting payment order creation for Order ID: {} with Amount: {}", orderId, amount);
        try {
            PaymentResponse response = paymentService.createOrder(orderId, amount);
            logger.info("Payment order created successfully for Order ID: {}", orderId);
            return ResponseEntity.ok(response);
        } catch (RazorpayException e) {
            logger.error("Error creating payment order for Order ID: {}: {}", orderId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during payment order creation for Order ID: {}: {}", orderId, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
            summary = "Handle Payment Webhook",
            description = "Receives and processes payment status updates from the Razorpay webhook."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Webhook received successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid webhook payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload) {
        logger.info("Received webhook payload: {}", payload);
        try {
            paymentService.webhookPayload(payload);
            logger.info("Webhook payload processed successfully");
            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            logger.error("Error processing webhook payload: {}", e.getMessage(), e);
            throw e;
        }
    }
}
