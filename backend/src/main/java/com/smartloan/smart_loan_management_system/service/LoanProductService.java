package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.LoanProductRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanProductResponse;

import java.util.List;

public interface LoanProductService {

    LoanProductResponse createProduct(LoanProductRequest request);

    List<LoanProductResponse> getAllProduct();

    LoanProductResponse getProductById(Long id);

    LoanProductResponse updateProduct(Long id,LoanProductRequest request);

    void deactivatedProduct(Long id);
}
