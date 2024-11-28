package com.main.dto;

import com.main.enums.PaymentMethod;
import com.main.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponse {
    private String paymentId;
    private Long orderId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private String razorPayPaymentId;
    private String razorPayOrderId;
}
