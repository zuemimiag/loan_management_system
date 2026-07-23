package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.RegionRequest;
import com.smartloan.smart_loan_management_system.dto_request.RegionResponse;
import com.smartloan.smart_loan_management_system.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    @Override
    public RegionResponse createRegion(RegionRequest request) {
        return null;
    }

    @Override
    public List<RegionResponse> getAllRegions() {
        return List.of();
    }

    @Override
    public RegionResponse getRegionById(Long id) {
        return null;
    }

    @Override
    public RegionResponse updatedRegion(Long id, RegionRequest request) {
        return null;
    }

    @Override
    public void deactivateRegion(Long id) {

    }
}
