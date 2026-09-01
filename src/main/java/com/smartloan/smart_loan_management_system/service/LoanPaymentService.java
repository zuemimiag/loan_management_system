package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentResponse;

import java.util.List;

public interface LoanPaymentService {

    LoanPaymentResponse createPayment(LoanPaymentRequest request);

    List<LoanPaymentResponse> getAllPayments();

    LoanPaymentResponse getPaymentById(Long id);

    List<LoanPaymentResponse> getPaymentsByLoanAccount(Long loanAccountId);

    LoanPaymentResponse updatePayment(
            Long id,
            LoanPaymentRequest request
    );

    void deactivatePayment(Long id);
}