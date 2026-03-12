package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.AssignInspectorRequest;
import com.example.BuildingPermitMs.dto.BuildingPermitRequest;
import com.example.BuildingPermitMs.entity.Applicant;
import com.example.BuildingPermitMs.entity.BuildingPermit;
import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.repository.ApplicantRepository;
import com.example.BuildingPermitMs.repository.BuildingPermitRepository;
import com.example.BuildingPermitMs.repository.InspectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BuildingPermitService {
    private final BuildingPermitRepository permitRepository;
    private final ApplicantRepository applicantRepository;
    private final InspectorRepository inspectorRepository;

    public BuildingPermitService(BuildingPermitRepository permitRepository, 
                                ApplicantRepository applicantRepository,
                                InspectorRepository inspectorRepository) {
        this.permitRepository = permitRepository;
        this.applicantRepository = applicantRepository;
        this.inspectorRepository = inspectorRepository;
    }

    public BuildingPermit submitPermit(BuildingPermitRequest request) {
        Applicant applicant = applicantRepository.findById(request.getApplicantId())
            .orElseThrow(() -> new RuntimeException("Applicant not found"));
        
        BuildingPermit permit = new BuildingPermit();
        permit.setProjectName(request.getProjectName());
        permit.setLocation(request.getLocation());
        permit.setApplicant(applicant);
        return permitRepository.save(permit);
    }

    public BuildingPermit assignInspectors(Long permitId, AssignInspectorRequest request) {
        BuildingPermit permit = permitRepository.findById(permitId)
            .orElseThrow(() -> new RuntimeException("Permit not found"));
        
        List<Inspector> inspectors = inspectorRepository.findAllById(request.getInspectorIds());
        permit.setInspectors(inspectors);
        permit.setStatus(BuildingPermit.PermitStatus.UNDER_INSPECTION);
        return permitRepository.save(permit);
    }

    public Page<BuildingPermit> getAllPermits(Pageable pageable) {
        return permitRepository.findAll(pageable);
    }

    public List<BuildingPermit> getAllPermits() {
        return permitRepository.findAll();
    }

    public BuildingPermit getPermitById(Long id) {
        return permitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permit not found"));
    }
}
