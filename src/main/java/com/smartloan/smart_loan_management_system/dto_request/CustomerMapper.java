package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.Branch;
import com.smartloan.smart_loan_management_system.entity.Customer;
import com.smartloan.smart_loan_management_system.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request){
        Customer customer = new Customer();

        customer.setCustomerName(request.getCustomerName());
        customer.setNrc(request.getNrc());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setGender(request.getGender());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setStatus(request.getStatus());

        return customer;
    }

    public CustomerResponse toResponse(Customer customer){
        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setCustomerName(customer.getCustomerName());
        response.setNrc(customer.getNrc());
        response.setDateOfBirth(customer.getDateOfBirth());
        response.setGender(customer.getGender());
        response.setPhone(customer.getPhone());
        response.setAddress(customer.getAddress());

        if(customer.getBranch() != null){
            response.setBranchId(customer.getBranch().getId());
            response.setBranchName(customer.getBranch().getBranchName());
        }

        if(customer.getLoanOfficer() != null){
            response.setLoanOfficerId(customer.getLoanOfficer().getId());
            response.setLoanOfficerName(customer.getLoanOfficer().getName());
        }
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());
        response.setStatus(customer.getStatus());
        return response;
    }

    public Customer updateCustomer(CustomerRequest request, Customer customer) {

        customer.setCustomerName(request.getCustomerName());
        customer.setNrc(request.getNrc());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setGender(request.getGender());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setStatus(request.getStatus());
        customer.setUpdatedAt(new Date());

        return customer;
    }
}
