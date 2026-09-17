package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.LoanAccountRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanAccountResponse;
import com.smartloan.smart_loan_management_system.service.LoanAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-accounts")
@RequiredArgsConstructor
public class LoanAccountController {

    private final LoanAccountService loanAccountService;

    @PostMapping
    public ResponseEntity<LoanAccountResponse> createLoanAccount(
            @RequestBody LoanAccountRequest request) {
        LoanAccountResponse response = loanAccountService.createLoanAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanAccountResponse>> getAllLoanAccounts() {
        return ResponseEntity.ok(loanAccountService.getAllLoanAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanAccountResponse> getLoanAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(loanAccountService.getLoanAccountById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanAccountResponse> updateLoanAccount(
            @PathVariable Long id, @RequestBody LoanAccountRequest request) {
        return ResponseEntity.ok(loanAccountService.updateLoanAccount(id, request));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateLoanAccount(@PathVariable Long id) {
        loanAccountService.deactivateLoanAccount(id);
        return ResponseEntity.ok("Loan account deactivated successfully.");
    }
}