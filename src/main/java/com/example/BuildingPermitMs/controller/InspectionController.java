package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.dto.InspectionRequest;
import com.example.BuildingPermitMs.entity.Inspection;
import com.example.BuildingPermitMs.service.InspectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inspections")
public class InspectionController {
    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping("/record")
    public ResponseEntity<Inspection> recordInspection(@RequestBody InspectionRequest request) {
        Inspection created = inspectionService.recordInspection(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Inspection>> getAllInspections() {
        return ResponseEntity.ok(inspectionService.getAllInspections());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Inspection>> getInspectionsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(inspectionService.getAllInspections(PageRequest.of(page, size)));
    }

    @GetMapping("/sorted")
    public ResponseEntity<Page<Inspection>> getInspectionsSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "inspectionDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(inspectionService.getAllInspections(
            PageRequest.of(page, size, Sort.by(sortDirection, sortBy))));
    }
}
