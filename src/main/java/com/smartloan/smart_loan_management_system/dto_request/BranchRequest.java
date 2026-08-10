package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BranchRequest {
    private String branchCode;
    private String branchName;
    private String address;
    private String phone;
    private String email;
    private String status;
    private Date updatedAt;
}
