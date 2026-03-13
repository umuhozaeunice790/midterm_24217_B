package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.BuildingPlanRequest;
import com.example.BuildingPermitMs.entity.BuildingPlan;
import com.example.BuildingPermitMs.entity.BuildingPermit;
import com.example.BuildingPermitMs.exception.ResourceNotFoundException;
import com.example.BuildingPermitMs.repository.BuildingPlanRepository;
import com.example.BuildingPermitMs.repository.BuildingPermitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuildingPlanService {
    private static final Logger logger = LoggerFactory.getLogger(BuildingPlanService.class);
    private final BuildingPlanRepository planRepository;
    private final BuildingPermitRepository permitRepository;

    public BuildingPlanService(BuildingPlanRepository planRepository, BuildingPermitRepository permitRepository) {
        this.planRepository = planRepository;
        this.permitRepository = permitRepository;
    }

    public BuildingPlan uploadPlan(BuildingPlanRequest request) {
        logger.info("🔄 Uploading building plan: {} for permit ID: {}", request.getFileName(), request.getPermitId());
        
        BuildingPermit permit = permitRepository.findById(request.getPermitId())
            .orElseThrow(() -> {
                logger.error("❌ Permit with ID {} not found", request.getPermitId());
                return new ResourceNotFoundException("Permit with ID " + request.getPermitId() + " not found");
            });
        
        BuildingPlan plan = new BuildingPlan();
        plan.setFileName(request.getFileName());
        plan.setFileUrl(request.getFileUrl());
        plan.setBuildingPermit(permit);
        
        BuildingPlan saved = planRepository.save(plan);
        logger.info("✅ Building plan uploaded successfully with ID: {}", saved.getId());
        return saved;
    }
}
