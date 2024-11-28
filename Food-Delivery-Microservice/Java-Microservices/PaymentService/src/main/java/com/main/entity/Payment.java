package com.main.entity;

import com.main.enums.PaymentMethod;
import com.main.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    private String paymentId;
    private Long orderId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private String razorPayPaymentId;
    private String razorPayOrderId;

}
