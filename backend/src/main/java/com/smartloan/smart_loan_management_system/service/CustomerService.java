package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.CustomerRequest;
import com.smartloan.smart_loan_management_system.dto_request.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    List<CustomerResponse> getAllCustomer();

    CustomerResponse getCustomerById(Long id);

    CustomerResponse updateCustomer(Long id,CustomerRequest request);

    void deactivatedCustomer(Long id);

}
