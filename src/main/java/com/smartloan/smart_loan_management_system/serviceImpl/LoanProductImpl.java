package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.LoanProductMapper;
import com.smartloan.smart_loan_management_system.dto_request.LoanProductRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanProductResponse;
import com.smartloan.smart_loan_management_system.entity.LoanProduct;
import com.smartloan.smart_loan_management_system.repository.LoanProductRepository;
import com.smartloan.smart_loan_management_system.service.LoanProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class LoanProductImpl implements LoanProductService {

    private final LoanProductRepository loanProductRepository;
    private final LoanProductMapper loanProductMapper;

    @Override
    public LoanProductResponse createProduct(LoanProductRequest request) {
        String productCode = request.getProductCode().trim().toUpperCase();
        if(loanProductRepository.existsByProductCode(productCode)){
            throw new RuntimeException("Loan Product code already exists.");
        }
        if(loanProductRepository.existsByProductName(request.getProductName().trim())){
            throw new RuntimeException("Loan Product name already exists.");
        }
        validateLoanProduct(request);

        LoanProduct loanProduct = loanProductMapper.toEntity(request);
        loanProduct.setStatus("ACTIVE");

        LoanProduct savedLoanProduct = loanProductRepository.save(loanProduct);

        return loanProductMapper.toResponse(savedLoanProduct);
    }

    @Override
    public List<LoanProductResponse> getAllProduct() {
        return loanProductRepository.findAll()
                .stream()
                .map(loanProductMapper::toResponse)
                .toList();
    }

    @Override
    public LoanProductResponse getProductById(Long id) {
        LoanProduct loanProduct = loanProductRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Loan Product not found."));
        return loanProductMapper.toResponse(loanProduct);
    }

    @Override
    public LoanProductResponse updateProduct(Long id, LoanProductRequest request) {
        LoanProduct loanProduct = loanProductRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Loan product not found."));

        String productCode = request.getProductCode().trim().toUpperCase();
        if(!loanProduct.getProductCode().equals(productCode)&& loanProductRepository.existsByProductCode(productCode)){
            throw new RuntimeException("Loan product code already exists.");
        }

        String productName = request.getProductName().trim().toUpperCase();
        if(!loanProduct.getProductName().equalsIgnoreCase(productName) && loanProductRepository.existsByProductName(productName)){
            throw new RuntimeException("Loan Product name already exists.");
        }
        validateLoanProduct(request);
        loanProduct.setProductCode(productCode);
        loanProduct.setProductName(productName);
        loanProduct.setInterestRate(request.getInterestRate());
        loanProduct.setMinimumAmount(request.getMinimumAmount());
        loanProduct.setMaximumAmount(request.getMaximumAmount());
        loanProduct.setDefaultTerm(request.getDefaultTerm());
        loanProduct.setPenaltyRate(request.getPenaltyRate());
        loanProduct.setDescription(request.getDescription());
        loanProduct.setUpdatedAt(new Date());
        LoanProduct updatedLoanProduct = loanProductRepository.save(loanProduct);

        return loanProductMapper.toResponse(updatedLoanProduct);
    }

    @Override
    public void deactivatedProduct(Long id) {
        LoanProduct loanProduct = loanProductRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Loan Product not found."));
        loanProduct.setStatus("INACTIVE");
        loanProduct.setUpdatedAt(new Date());
        loanProductRepository.save(loanProduct);

    }

    private void validateLoanProduct(LoanProductRequest request){
        if(request.getInterestRate()==null || request.getInterestRate().compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Interest rate must be greater than zero.");
        }
        if(request.getMinimumAmount() == null || request.getMinimumAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Minimum amount must be greater than zero.");
        }
        if(request.getMaximumAmount() == null || request.getMaximumAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("Maximum amount must be greater than zero.");
        }
        if(request.getDefaultTerm() == null || request.getDefaultTerm()<=0){
            throw new RuntimeException("Loan term must be greater than zero.");
        }
        if(request.getPenaltyRate() != null && request.getPenaltyRate().compareTo(BigDecimal.ZERO)< 0 ){
            throw new RuntimeException("Penalty rate cannot be negative.");
        }

        if (request.getMinimumAmount()
                .compareTo(request.getMaximumAmount()) > 0) {

            throw new RuntimeException(
                    "Minimum amount cannot be greater than maximum amount.");
        }

    }
}
