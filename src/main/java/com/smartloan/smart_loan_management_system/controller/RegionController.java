package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.RegionRequest;
import com.smartloan.smart_loan_management_system.dto_request.RegionResponse;
import com.smartloan.smart_loan_management_system.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions/")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<RegionResponse> createRegion(RegionRequest request){
        RegionResponse response = regionService.createRegion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RegionResponse>> getAllRegions(){
        return ResponseEntity.ok(regionService.getAllRegions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionResponse> getRegionById(@PathVariable Long id){
        return ResponseEntity.ok(regionService.getRegionById(id));
    }

    @PutMapping
    public ResponseEntity<RegionResponse> updatedRegion(@PathVariable Long id,
                                                        @RequestBody RegionRequest request){
        return ResponseEntity.ok(regionService.updatedRegion(id,request));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivatedRegion(@PathVariable Long id){
        regionService.deactivateRegion(id);
        return ResponseEntity.ok("Region deactivated successfully.");
    }
}
