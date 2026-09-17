package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.LoanPayment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoanPaymentMapper {

    public LoanPayment toEntity(LoanPaymentRequest request) {

        LoanPayment payment = new LoanPayment();

        payment.setPaymentAmount(request.getPaymentAmount());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(request.getStatus());

        return payment;
    }

    public LoanPaymentResponse toResponse(LoanPayment payment) {

        LoanPaymentResponse response = new LoanPaymentResponse();

        response.setId(payment.getId());

        if (payment.getLoanAccount() != null) {

            response.setLoanAccountId(
                    payment.getLoanAccount().getId()
            );

            response.setAccountNumber(
                    payment.getLoanAccount().getAccountNumber()
            );

            if (payment.getLoanAccount().getCustomer() != null) {
                response.setCustomerName(
                        payment.getLoanAccount()
                                .getCustomer()
                                .getCustomerName()
                );
            }
        }

        response.setPaymentAmount(payment.getPaymentAmount());
        response.setPrincipalAmount(payment.getPrincipalAmount());
        response.setInterestAmount(payment.getInterestAmount());
        response.setPenaltyAmount(payment.getPenaltyAmount());

        response.setPaymentDate(payment.getPaymentDate());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getStatus());

        response.setCreatedAt(payment.getCreatedAt());
        response.setUpdatedAt(payment.getUpdatedAt());

        return response;
    }

    public LoanPayment updatePayment(
            LoanPaymentRequest request,
            LoanPayment payment) {

        payment.setPaymentAmount(request.getPaymentAmount());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(request.getStatus());
        payment.setUpdatedAt(new Date());

        return payment;
    }
}