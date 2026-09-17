package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment payment) {

        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());

        if (payment.getLoanAccount() != null) {
            response.setLoanAccountId(
                    payment.getLoanAccount().getId()
            );

            response.setAccountNumber(
                    payment.getLoanAccount().getAccountNumber()
            );
        }

        if (payment.getRepaymentSchedule() != null) {
            response.setRepaymentScheduleId(
                    payment.getRepaymentSchedule().getId()
            );

            response.setInstallmentNo(
                    payment.getRepaymentSchedule().getInstallmentNo()
            );
        }

        response.setAmount(payment.getAmount());
        response.setPenaltyAmount(payment.getPenaltyAmount());
        response.setInterestAmount(payment.getInterestAmount());
        response.setPrincipalAmount(payment.getPrincipalAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setReferenceNumber(payment.getReferenceNumber());
        response.setPaymentDate(payment.getPaymentDate());
        response.setStatus(payment.getStatus());
        response.setCreatedAt(payment.getCreatedAt());
        response.setUpdatedAt(payment.getUpdatedAt());

        return response;
    }
}