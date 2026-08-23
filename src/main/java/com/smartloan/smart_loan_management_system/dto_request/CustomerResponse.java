package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CustomerResponse {

    private Long id;
    private String customerName;
    private String nrc;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String address;
    private Long branchId;
    private String branchName;
    private Long loanOfficerId;
    private String loanOfficerName;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
