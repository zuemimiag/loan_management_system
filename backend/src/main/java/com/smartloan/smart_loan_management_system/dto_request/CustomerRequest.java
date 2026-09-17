package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerRequest {
    private String customerName;
    private String nrc;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String address;
    private Long branchId;
    private Long loId;
    private String status;

}
