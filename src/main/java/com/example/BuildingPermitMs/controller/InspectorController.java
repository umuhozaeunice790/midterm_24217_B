package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.service.InspectorService;
import org.springframework.http.HttpStatus;
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
       Inspector created = inspectorService.createInspector(inspector);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Inspector>> getAllInspectors() {
        return ResponseEntity.ok(inspectorService.getAllInspectors());
    }
}
