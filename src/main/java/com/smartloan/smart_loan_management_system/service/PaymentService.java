package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.PaymentRequest;
import com.smartloan.smart_loan_management_system.dto_request.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    List<PaymentResponse> getAllPayments();

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getPaymentsByLoanAccount(Long loanAccountId);
}