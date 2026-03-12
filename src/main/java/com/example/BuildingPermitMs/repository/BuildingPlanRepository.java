package com.example.BuildingPermitMs.repository;

import com.example.BuildingPermitMs.entity.BuildingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BuildingPlanRepository extends JpaRepository<BuildingPlan, Long> {
    Optional<BuildingPlan> findByBuildingPermitId(Long permitId);
}
