package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String employeeId;
    private String name;
    private String email;
    private String roleName;
    private String phone;
    private String branchName;
    private String status;
    private Date lastLogin;
    private Date createdAt;
    private Date updatedAt;
}
