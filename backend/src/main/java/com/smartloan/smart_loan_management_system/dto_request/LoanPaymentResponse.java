package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class LoanPaymentResponse {

    private Long id;

    private Long loanAccountId;
    private String accountNumber;

    private String customerName;

    private BigDecimal paymentAmount;
    private BigDecimal principalAmount;
    private BigDecimal interestAmount;
    private BigDecimal penaltyAmount;

    private Date paymentDate;

    private String paymentMethod;
    private String status;

    private Date createdAt;
    private Date updatedAt;
}