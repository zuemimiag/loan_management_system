package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class LoanPaymentRequest {

    private Long loanAccountId;

    private BigDecimal paymentAmount;

    private Date paymentDate;

    private String paymentMethod;

    private String status;
}