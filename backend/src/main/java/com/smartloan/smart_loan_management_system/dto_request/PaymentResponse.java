package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class PaymentResponse {

    private Long id;

    private Long loanAccountId;
    private String accountNumber;

    private Long repaymentScheduleId;
    private Integer installmentNo;

    private BigDecimal amount;

    private BigDecimal penaltyAmount;
    private BigDecimal interestAmount;
    private BigDecimal principalAmount;

    private String paymentMethod;
    private String referenceNumber;

    private Date paymentDate;

    private String status;

    private Date createdAt;
    private Date updatedAt;
}