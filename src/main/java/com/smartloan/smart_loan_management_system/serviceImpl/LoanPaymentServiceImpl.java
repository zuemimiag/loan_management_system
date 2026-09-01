package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentMapper;
import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentResponse;
import com.smartloan.smart_loan_management_system.entity.LoanAccount;
import com.smartloan.smart_loan_management_system.entity.LoanPayment;
import com.smartloan.smart_loan_management_system.repository.LoanAccountRepository;
import com.smartloan.smart_loan_management_system.repository.LoanPaymentRepository;
import com.smartloan.smart_loan_management_system.service.LoanPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanPaymentServiceImpl implements LoanPaymentService {

    private final LoanPaymentRepository loanPaymentRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final LoanPaymentMapper loanPaymentMapper;

    @Override
    public LoanPaymentResponse createPayment(
            LoanPaymentRequest request) {

        LoanAccount loanAccount =
                loanAccountRepository.findById(request.getLoanAccountId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan Account not found."
                                ));

        if (loanAccount.getStatus() == null ||
                !loanAccount.getStatus().equalsIgnoreCase("ACTIVE")) {

            throw new RuntimeException(
                    "Payment cannot be made for an inactive loan account."
            );
        }

        if (request.getPaymentAmount() == null ||
                request.getPaymentAmount().signum() <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero."
            );
        }

        if (request.getPaymentDate() == null) {
            throw new RuntimeException(
                    "Payment date is required."
            );
        }

        LoanPayment payment =
                loanPaymentMapper.toEntity(request);

        payment.setLoanAccount(loanAccount);

        /*
         * Principal / Interest calculation
         * will be implemented in the next step.
         */

        payment.setPrincipalAmount(request.getPaymentAmount());
        payment.setInterestAmount(
                payment.getPaymentAmount()
        );

        payment.setPenaltyAmount(
                java.math.BigDecimal.ZERO
        );

        payment.setCreatedAt(new Date());
        payment.setUpdatedAt(new Date());

        LoanPayment savedPayment =
                loanPaymentRepository.save(payment);

        return loanPaymentMapper.toResponse(savedPayment);
    }

    @Override
    public List<LoanPaymentResponse> getAllPayments() {

        return loanPaymentRepository.findAll()
                .stream()
                .map(loanPaymentMapper::toResponse)
                .toList();
    }

    @Override
    public LoanPaymentResponse getPaymentById(Long id) {

        LoanPayment payment =
                loanPaymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."
                                ));

        return loanPaymentMapper.toResponse(payment);
    }

    @Override
    public List<LoanPaymentResponse> getPaymentsByLoanAccount(
            Long loanAccountId) {

        if (!loanAccountRepository.existsById(loanAccountId)) {
            throw new RuntimeException(
                    "Loan Account not found."
            );
        }

        return loanPaymentRepository
                .findByLoanAccountId(loanAccountId)
                .stream()
                .map(loanPaymentMapper::toResponse)
                .toList();
    }

    @Override
    public LoanPaymentResponse updatePayment(
            Long id,
            LoanPaymentRequest request) {

        LoanPayment payment =
                loanPaymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."
                                ));

        loanPaymentMapper.updatePayment(request, payment);

        LoanPayment updatedPayment =
                loanPaymentRepository.save(payment);

        return loanPaymentMapper.toResponse(updatedPayment);
    }

    @Override
    public void deactivatePayment(Long id) {

        LoanPayment payment =
                loanPaymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."
                                ));

        payment.setStatus("INACTIVE");
        payment.setUpdatedAt(new Date());

        loanPaymentRepository.save(payment);
    }
}