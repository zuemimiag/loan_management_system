package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RoleResponse {
    private Long id;
    private String roleName;
    private String description;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
