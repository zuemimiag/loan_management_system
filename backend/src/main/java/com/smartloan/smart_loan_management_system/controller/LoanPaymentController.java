package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentResponse;
import com.smartloan.smart_loan_management_system.service.LoanPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class LoanPaymentController {

    private final LoanPaymentService loanPaymentService;

    @PostMapping
    public ResponseEntity<LoanPaymentResponse> createPayment(
            @RequestBody LoanPaymentRequest request) {

        LoanPaymentResponse response =
                loanPaymentService.createPayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanPaymentResponse>> getAllPayments() {

        return ResponseEntity.ok(
                loanPaymentService.getAllPayments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanPaymentResponse> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                loanPaymentService.getPaymentById(id)
        );
    }

    @GetMapping("/loan-account/{loanAccountId}")
    public ResponseEntity<List<LoanPaymentResponse>>
    getPaymentsByLoanAccount(
            @PathVariable Long loanAccountId) {

        return ResponseEntity.ok(
                loanPaymentService
                        .getPaymentsByLoanAccount(loanAccountId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanPaymentResponse> updatePayment(
            @PathVariable Long id,
            @RequestBody LoanPaymentRequest request) {

        return ResponseEntity.ok(
                loanPaymentService.updatePayment(id, request)
        );
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivatePayment(
            @PathVariable Long id) {

        loanPaymentService.deactivatePayment(id);

        return ResponseEntity.ok(
                "Payment deactivated successfully."
        );
    }
}