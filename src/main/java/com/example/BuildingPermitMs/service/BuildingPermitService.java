package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.AssignInspectorRequest;
import com.example.BuildingPermitMs.dto.BuildingPermitRequest;
import com.example.BuildingPermitMs.entity.Applicant;
import com.example.BuildingPermitMs.entity.BuildingPermit;
import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.exception.ResourceNotFoundException;
import com.example.BuildingPermitMs.repository.ApplicantRepository;
import com.example.BuildingPermitMs.repository.BuildingPermitRepository;
import com.example.BuildingPermitMs.repository.InspectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BuildingPermitService {
    private static final Logger logger = LoggerFactory.getLogger(BuildingPermitService.class);
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
        logger.info("🔄 Submitting building permit for project: {}", request.getProjectName());
        
        Applicant applicant = applicantRepository.findById(request.getApplicantId())
            .orElseThrow(() -> {
                logger.error("❌ Applicant with ID {} not found", request.getApplicantId());
                return new ResourceNotFoundException("Applicant with ID " + request.getApplicantId() + " not found");
            });
        
        BuildingPermit permit = new BuildingPermit();
        permit.setProjectName(request.getProjectName());
        permit.setLocation(request.getLocation());
        permit.setApplicant(applicant);
        
        BuildingPermit saved = permitRepository.save(permit);
        logger.info("✅ Building permit submitted successfully with ID: {}", saved.getId());
        return saved;
    }

    public BuildingPermit assignInspectors(Long permitId, AssignInspectorRequest request) {
        logger.info("🔄 Assigning inspectors to permit ID: {}", permitId);
        
        BuildingPermit permit = permitRepository.findById(permitId)
            .orElseThrow(() -> {
                logger.error("❌ Permit with ID {} not found", permitId);
                return new ResourceNotFoundException("Permit with ID " + permitId + " not found");
            });
        
        List<Inspector> inspectors = inspectorRepository.findAllById(request.getInspectorIds());
        if (inspectors.isEmpty()) {
            logger.error("❌ No inspectors found with provided IDs");
            throw new ResourceNotFoundException("No inspectors found with provided IDs");
        }
        
        permit.setInspectors(inspectors);
        permit.setStatus(BuildingPermit.PermitStatus.UNDER_INSPECTION);
        
        BuildingPermit saved = permitRepository.save(permit);
        logger.info("✅ Assigned {} inspectors to permit ID: {}", inspectors.size(), permitId);
        return saved;
    }

    public Page<BuildingPermit> getAllPermits(Pageable pageable) {
        logger.info("📋 Fetching permits with pagination - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<BuildingPermit> page = permitRepository.findAll(pageable);
        logger.info("✅ Retrieved {} permits out of {} total", page.getNumberOfElements(), page.getTotalElements());
        return page;
    }

    public List<BuildingPermit> getAllPermits() {
        logger.info("📋 Fetching all permits");
        List<BuildingPermit> permits = permitRepository.findAll();
        logger.info("✅ Found {} permits", permits.size());
        return permits;
    }

    public BuildingPermit getPermitById(Long id) {
        logger.info("📋 Fetching permit with ID: {}", id);
        return permitRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("❌ Permit with ID {} not found", id);
                return new ResourceNotFoundException("Permit with ID " + id + " not found");
            });
    }
}
