package com.main.dto;

import com.main.enums.PaymentMethod;
import com.main.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for handling payment requests. This class encapsulates details such as
 * the order ID, payment amount, date, method, and status for making payments.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for processing payments")
public class PaymentRequest {

    @Schema(description = "Unique identifier of the order associated with the payment", example = "12345")
    private Long orderId;

    @Schema(description = "Amount to be paid", example = "249.99")
    private BigDecimal amount;

    @Schema(description = "Date and time when the payment was made", example = "2024-11-20T14:30:00")
    private LocalDateTime paymentDate;

    @Schema(description = "Payment method used for the transaction", example = "CREDIT_CARD")
    private String paymentMethod;

    @Schema(description = "Current status of the payment", example = "COMPLETED")
    private PaymentStatus paymentStatus;
}
