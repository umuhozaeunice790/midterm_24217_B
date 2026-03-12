package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.dto.BuildingPlanRequest;
import com.example.BuildingPermitMs.entity.BuildingPlan;
import com.example.BuildingPermitMs.service.BuildingPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
public class BuildingPlanController {
    private final BuildingPlanService planService;

    public BuildingPlanController(BuildingPlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/upload")
    public ResponseEntity<BuildingPlan> uploadPlan(@RequestBody BuildingPlanRequest request) {
        return ResponseEntity.ok(planService.uploadPlan(request));
    }
}
