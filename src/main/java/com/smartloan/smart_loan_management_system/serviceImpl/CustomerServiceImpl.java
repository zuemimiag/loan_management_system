package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.CustomerMapper;
import com.smartloan.smart_loan_management_system.dto_request.CustomerRequest;
import com.smartloan.smart_loan_management_system.dto_request.CustomerResponse;
import com.smartloan.smart_loan_management_system.entity.Branch;
import com.smartloan.smart_loan_management_system.entity.Customer;
import com.smartloan.smart_loan_management_system.entity.User;
import com.smartloan.smart_loan_management_system.exception.BranchNotFoundException;
import com.smartloan.smart_loan_management_system.exception.UserNotFoundException;
import com.smartloan.smart_loan_management_system.repository.BranchRepository;
import com.smartloan.smart_loan_management_system.repository.CustomerRepository;
import com.smartloan.smart_loan_management_system.repository.UserRepository;
import com.smartloan.smart_loan_management_system.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        // 1. Customer name
        if (request.getCustomerName() == null ||
                request.getCustomerName().trim().isEmpty()) {

            throw new RuntimeException("Customer name is required.");
        }

        // 2. NRC
        if (request.getNrc() == null ||
                request.getNrc().trim().isEmpty()) {
            throw new RuntimeException("NRC is required.");
        }

        String nrc = request.getNrc().trim().toUpperCase();
        if (customerRepository.existsByNrc(nrc)) {
            throw new RuntimeException("NRC already exists.");
        }

        // 3. Branch
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new BranchNotFoundException(
                                "Branch not found."));

        if (!"ACTIVE".equalsIgnoreCase(branch.getStatus())) {
            throw new RuntimeException(
                    "Branch is inactive.");
        }

        // 4. Loan Officer
        User loanOfficer = userRepository.findById(request.getLoId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Loan Officer not found."));

        if (!"ACTIVE".equalsIgnoreCase(loanOfficer.getStatus())) {
            throw new RuntimeException(
                    "Loan Officer is inactive.");
        }

        if (loanOfficer.getRole() == null ||
                !"LOAN_OFFICER".equalsIgnoreCase(
                        loanOfficer.getRole().getRoleName())) {

            throw new RuntimeException(
                    "Selected user is not a Loan Officer.");
        }

        if (request.getDateOfBirth() == null) {
            throw new RuntimeException("Date of birth is required.");
        }

        if (request.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new RuntimeException("Date of birth cannot be in the future.");
        }

        if (request.getGender() == null ||
                request.getGender().trim().isEmpty()) {
            throw new RuntimeException("Gender is required.");
        }

        if (request.getStatus() == null ||
                (!request.getStatus().equalsIgnoreCase("ACTIVE")
                        && !request.getStatus().equalsIgnoreCase("INACTIVE"))) {

            throw new RuntimeException(
                    "Status must be ACTIVE or INACTIVE.");
        }

        // 5. Create
        Customer customer = customerMapper.toEntity(request);

        customer.setNrc(nrc);
        customer.setBranch(branch);
        customer.setLoanOfficer(loanOfficer);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        customer.setStatus("ACTIVE");

        Customer savedCustomer =
                customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Customer Not Found"));
        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Customer Not Found."));

        String nrc = request.getNrc().trim().toUpperCase();
        if(!customer.getNrc().equalsIgnoreCase(request.getNrc()) &&
        customerRepository.existsByNrc(request.getNrc())){
            throw new RuntimeException("NRC already exists.");
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(()->new BranchNotFoundException("Branch Not Found."));
        User loanOfficer = userRepository.findById(request.getLoId())
                .orElseThrow(()->new UserNotFoundException("Loan Officer Not Found."));

        customerMapper.updateCustomer(request,customer);
        customer.setBranch(branch);
        customer.setLoanOfficer(loanOfficer);
        customer.setUpdatedAt(new Date());

        Customer saveCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(saveCustomer);
    }

    @Override
    public void deactivatedCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Customer Not found."));
        customer.setStatus("INACTIVE");
        customer.setUpdatedAt(new Date());

        customerRepository.save(customer);

    }
}
