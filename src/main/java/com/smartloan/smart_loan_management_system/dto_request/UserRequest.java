package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String employeeId;
    private String email;
    private String phone;
    private String password;
    private Long roleId;
    private String status;
    private String branchId;
}
