package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionRequest {

    private String regionCode;
    private String regionName;
    private String managerName;
    private String status;

}
