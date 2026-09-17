package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    private Long loanAccountId;

    private Long repaymentScheduleId;

    private BigDecimal amount;

    private String paymentMethod;

    private String referenceNumber;
}