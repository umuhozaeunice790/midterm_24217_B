package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.dto.BuildingPlanRequest;
import com.example.BuildingPermitMs.entity.BuildingPlan;
import com.example.BuildingPermitMs.service.BuildingPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
public class BuildingPlanController {
    private static final Logger logger = LoggerFactory.getLogger(BuildingPlanController.class);
    private final BuildingPlanService planService;

    public BuildingPlanController(BuildingPlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/upload")
    public ResponseEntity<BuildingPlan> uploadPlan(@RequestBody BuildingPlanRequest request) {
        logger.info("📥 POST /api/plans/upload - Uploading plan: {}", request.getFileName());
        BuildingPlan created = planService.uploadPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
