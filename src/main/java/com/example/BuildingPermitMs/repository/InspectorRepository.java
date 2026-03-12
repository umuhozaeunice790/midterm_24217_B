package com.example.BuildingPermitMs.repository;

import com.example.BuildingPermitMs.entity.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    boolean existsByLicenseNumber(String licenseNumber);
}
