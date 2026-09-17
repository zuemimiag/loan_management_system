package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.LoanProductRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanProductResponse;
import com.smartloan.smart_loan_management_system.service.LoanProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-products")
@RequiredArgsConstructor
public class LoanProductController {
    private final LoanProductService loanProductService;

    @PostMapping
    public ResponseEntity<LoanProductResponse> createLoanProduct(@RequestBody LoanProductRequest request){
        LoanProductResponse response = loanProductService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<List<LoanProductResponse>> getAllLoanProducts(){
        return ResponseEntity.ok(loanProductService.getAllProduct());
    }
    @GetMapping("/{id}")
    public ResponseEntity<LoanProductResponse> getLoanProductById(@PathVariable Long id){
        return ResponseEntity.ok(loanProductService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanProductResponse> updateLoanProduct(@PathVariable Long id,
                                                                 @RequestBody LoanProductRequest request){
        return ResponseEntity.ok(loanProductService.updateProduct(id,request));
    }
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateLoanProduct(@PathVariable Long id){
        loanProductService.deactivatedProduct(id);
        return ResponseEntity.ok("Loan product deactivated successfully.");
    }

}
