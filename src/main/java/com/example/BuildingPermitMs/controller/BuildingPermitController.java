package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.dto.AssignInspectorRequest;
import com.example.BuildingPermitMs.dto.BuildingPermitRequest;
import com.example.BuildingPermitMs.entity.BuildingPermit;
import com.example.BuildingPermitMs.service.BuildingPermitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/permits")
public class BuildingPermitController {
    private final BuildingPermitService permitService;

    public BuildingPermitController(BuildingPermitService permitService) {
        this.permitService = permitService;
    }

    @PostMapping("/submit")
    public ResponseEntity<BuildingPermit> submitPermit(@RequestBody BuildingPermitRequest request) {
        return ResponseEntity.ok(permitService.submitPermit(request));
    }

    @PostMapping("/{permitId}/assign-inspectors")
    public ResponseEntity<BuildingPermit> assignInspectors(
            @PathVariable Long permitId,
            @RequestBody AssignInspectorRequest request) {
        return ResponseEntity.ok(permitService.assignInspectors(permitId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingPermit> getPermitById(@PathVariable Long id) {
        return ResponseEntity.ok(permitService.getPermitById(id));
    }

    @GetMapping
    public ResponseEntity<List<BuildingPermit>> getAllPermits() {
        return ResponseEntity.ok(permitService.getAllPermits());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<BuildingPermit>> getPermitsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(permitService.getAllPermits(PageRequest.of(page, size)));
    }

    @GetMapping("/sorted")
    public ResponseEntity<Page<BuildingPermit>> getPermitsSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "submissionDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(permitService.getAllPermits(
            PageRequest.of(page, size, Sort.by(sortDirection, sortBy))));
    }
}
