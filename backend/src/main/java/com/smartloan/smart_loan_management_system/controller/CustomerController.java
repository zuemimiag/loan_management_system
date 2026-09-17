package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.CustomerRequest;
import com.smartloan.smart_loan_management_system.dto_request.CustomerResponse;
import com.smartloan.smart_loan_management_system.entity.Customer;
import com.smartloan.smart_loan_management_system.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse>updatedCustomer(@PathVariable Long id,
                                                           @RequestBody CustomerRequest request){
        return ResponseEntity.ok(customerService.updateCustomer(id,request));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivatedCustomer(@PathVariable Long id){
        customerService.deactivatedCustomer(id);
        return ResponseEntity.ok("Customer deactivated successfully");
    }
}
