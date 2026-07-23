package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.RegionRequest;
import com.smartloan.smart_loan_management_system.dto_request.RegionResponse;

import java.util.List;

public interface RegionService {

    RegionResponse createRegion(RegionRequest request);

    List<RegionResponse> getAllRegions();

    RegionResponse getRegionById(Long id);

    RegionResponse updatedRegion(Long id,RegionRequest request);

    void deactivateRegion(Long id);
}
