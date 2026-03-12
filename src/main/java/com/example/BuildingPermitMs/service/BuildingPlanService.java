package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.BuildingPlanRequest;
import com.example.BuildingPermitMs.entity.BuildingPlan;
import com.example.BuildingPermitMs.entity.BuildingPermit;
import com.example.BuildingPermitMs.repository.BuildingPlanRepository;
import com.example.BuildingPermitMs.repository.BuildingPermitRepository;
import org.springframework.stereotype.Service;

@Service
public class BuildingPlanService {
    private final BuildingPlanRepository planRepository;
    private final BuildingPermitRepository permitRepository;

    public BuildingPlanService(BuildingPlanRepository planRepository, BuildingPermitRepository permitRepository) {
        this.planRepository = planRepository;
        this.permitRepository = permitRepository;
    }

    public BuildingPlan uploadPlan(BuildingPlanRequest request) {
        BuildingPermit permit = permitRepository.findById(request.getPermitId())
            .orElseThrow(() -> new RuntimeException("Permit not found"));
        
        BuildingPlan plan = new BuildingPlan();
        plan.setFileName(request.getFileName());
        plan.setFileUrl(request.getFileUrl());
        plan.setBuildingPermit(permit);
        return planRepository.save(plan);
    }
}
