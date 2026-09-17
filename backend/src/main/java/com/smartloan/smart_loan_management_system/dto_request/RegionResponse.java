package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegionResponse {
    private Long id;
    private String regionCode;
    private String regionName;
    private String managerName;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
