package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.dto.ApplicantRequest;
import com.example.BuildingPermitMs.entity.Applicant;
import com.example.BuildingPermitMs.service.ApplicantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {
    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping("/register")
    public ResponseEntity<Applicant> registerApplicant(@RequestBody ApplicantRequest request) {
        Applicant created = applicantService.registerApplicant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/province/name/{provinceName}")
    public ResponseEntity<List<Applicant>> getByProvinceName(@PathVariable String provinceName) {
        return ResponseEntity.ok(applicantService.getApplicantsByProvinceName(provinceName));
    }

    @GetMapping("/province/code/{provinceCode}")
    public ResponseEntity<List<Applicant>> getByProvinceCode(@PathVariable String provinceCode) {
        return ResponseEntity.ok(applicantService.getApplicantsByProvinceCode(provinceCode));
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        return ResponseEntity.ok(applicantService.getAllApplicants());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Applicant>> getApplicantsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(applicantService.getAllApplicants(PageRequest.of(page, size)));
    }

    @GetMapping("/sorted")
    public ResponseEntity<Page<Applicant>> getApplicantsSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(applicantService.getAllApplicants(
            PageRequest.of(page, size, Sort.by(sortDirection, sortBy))));
    }
}
