package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.service.InspectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inspectors")
public class InspectorController {
    private final InspectorService inspectorService;

    public InspectorController(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    @PostMapping
    public ResponseEntity<Inspector> createInspector(@RequestBody Inspector inspector) {
        return ResponseEntity.ok(inspectorService.createInspector(inspector));
    }

    @GetMapping
    public ResponseEntity<List<Inspector>> getAllInspectors() {
        return ResponseEntity.ok(inspectorService.getAllInspectors());
    }
}
