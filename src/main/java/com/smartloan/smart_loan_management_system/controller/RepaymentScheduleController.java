package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.RepaymentScheduleResponse;
import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;
import com.smartloan.smart_loan_management_system.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repayment-schedules")
@RequiredArgsConstructor
public class RepaymentScheduleController {

    private final RepaymentScheduleService repaymentScheduleService;

    // Generate repayment schedule for a loan account
    @PostMapping("/generate/{loanAccountId}")
    public ResponseEntity<List<RepaymentScheduleResponse>> generateSchedule(
            @PathVariable Long loanAccountId) {

        return ResponseEntity.ok(
                repaymentScheduleService.generateSchedule(loanAccountId)
        );
    }

    // Get repayment schedule by loan account
    @GetMapping("/loan-account/{loanAccountId}")
    public ResponseEntity<List<RepaymentScheduleResponse>> getScheduleByLoanAccount(
            @PathVariable Long loanAccountId) {

        return ResponseEntity.ok(
                repaymentScheduleService.getScheduleByLoanAccount(loanAccountId)
        );
    }

    // Get entity records by loan account ID
    @GetMapping("/loan-account/{loanAccountId}/details")
    public ResponseEntity<List<RepaymentSchedule>> findByLoanAccountId(
            @PathVariable Long loanAccountId) {

        return ResponseEntity.ok(
                repaymentScheduleService.findByLoanAccountId(loanAccountId)
        );
    }
}