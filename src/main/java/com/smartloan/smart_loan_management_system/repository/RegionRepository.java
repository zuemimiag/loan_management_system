package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.Regions;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Regions, Long> {

    Optional<Regions> findByRegionCode(String regionCode);

    Optional<Regions> findByRegionName(String regionName);

    boolean existsByRegionCode(String regionCode);

    boolean existsByRegionName(String regionName);
}
