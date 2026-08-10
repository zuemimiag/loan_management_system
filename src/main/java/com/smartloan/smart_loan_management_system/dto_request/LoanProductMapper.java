package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.LoanProduct;
import org.springframework.stereotype.Component;

@Component
public class LoanProductMapper {

    public LoanProduct toEntity(LoanProductRequest request){
        LoanProduct loanProduct = new LoanProduct();
        loanProduct.setProductCode(request.getProductCode());
        loanProduct.setProductName(request.getProductName());
        loanProduct.setDescription(request.getDescription());
        loanProduct.setStatus(request.getStatus());
        loanProduct.setMaximumAmount(request.getMaximumAmount());
        loanProduct.setMinimumAmount(request.getMinimumAmount());
        loanProduct.setInterestRate(request.getInterestRate());
        loanProduct.setDefaultTerm(request.getDefaultTerm());
        loanProduct.setPenaltyRate(request.getPenaltyRate());
        return loanProduct;
    }

    public LoanProductResponse toResponse(LoanProduct product){
        LoanProductResponse response = new LoanProductResponse();
        response.setId(product.getId());
        response.setProductCode(product.getProductCode());
        response.setProductName(product.getProductName());
        response.setDescription(product.getDescription());
        response.setDefaultTerm(product.getDefaultTerm());
        response.setStatus(product.getStatus());
        response.setMaximumAmount(product.getMaximumAmount());
        response.setMinimumAmount(product.getMinimumAmount());
        response.setInterestRate(product.getInterestRate());
        response.setPenaltyRate(product.getPenaltyRate());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }

    public LoanProduct updatedProduct(LoanProductRequest request,LoanProduct loanProduct){
        loanProduct.setProductCode(request.getProductCode());
        loanProduct.setProductName(request.getProductName());
        loanProduct.setDescription(request.getDescription());
        loanProduct.setPenaltyRate(request.getPenaltyRate());
        loanProduct.setMaximumAmount(request.getMaximumAmount());
        loanProduct.setMinimumAmount(request.getMinimumAmount());
        loanProduct.setStatus(request.getStatus());
        loanProduct.setDefaultTerm(request.getDefaultTerm());
        return loanProduct;
    }
}
