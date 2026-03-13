package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.InspectionRequest;
import com.example.BuildingPermitMs.entity.BuildingPermit;
import com.example.BuildingPermitMs.entity.Inspection;
import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.exception.ResourceNotFoundException;
import com.example.BuildingPermitMs.repository.BuildingPermitRepository;
import com.example.BuildingPermitMs.repository.InspectionRepository;
import com.example.BuildingPermitMs.repository.InspectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InspectionService {
    private static final Logger logger = LoggerFactory.getLogger(InspectionService.class);
    private final InspectionRepository inspectionRepository;
    private final BuildingPermitRepository permitRepository;
    private final InspectorRepository inspectorRepository;

    public InspectionService(InspectionRepository inspectionRepository, 
                           BuildingPermitRepository permitRepository,
                           InspectorRepository inspectorRepository) {
        this.inspectionRepository = inspectionRepository;
        this.permitRepository = permitRepository;
        this.inspectorRepository = inspectorRepository;
    }

    public Inspection recordInspection(InspectionRequest request) {
        logger.info("🔄 Recording inspection for permit ID: {} by inspector ID: {}", request.getPermitId(), request.getInspectorId());
        
        BuildingPermit permit = permitRepository.findById(request.getPermitId())
            .orElseThrow(() -> {
                logger.error("❌ Permit with ID {} not found", request.getPermitId());
                return new ResourceNotFoundException("Permit with ID " + request.getPermitId() + " not found");
            });
            
        Inspector inspector = inspectorRepository.findById(request.getInspectorId())
            .orElseThrow(() -> {
                logger.error("❌ Inspector with ID {} not found", request.getInspectorId());
                return new ResourceNotFoundException("Inspector with ID " + request.getInspectorId() + " not found");
            });
        
        Inspection inspection = new Inspection();
        inspection.setBuildingPermit(permit);
        inspection.setInspector(inspector);
        inspection.setResult(Inspection.InspectionResult.valueOf(request.getResult()));
        inspection.setRemarks(request.getRemarks());
        
        Inspection saved = inspectionRepository.save(inspection);
        logger.info("✅ Inspection recorded with result: {}", inspection.getResult());
        
        if (inspection.getResult() == Inspection.InspectionResult.PASSED) {
            permit.setStatus(BuildingPermit.PermitStatus.APPROVED);
            permitRepository.save(permit);
            logger.info("✅ Permit ID {} status updated to APPROVED", permit.getId());
        }
        
        return saved;
    }

    public Page<Inspection> getAllInspections(Pageable pageable) {
        logger.info("📋 Fetching inspections with pagination - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Inspection> page = inspectionRepository.findAll(pageable);
        logger.info("✅ Retrieved {} inspections out of {} total", page.getNumberOfElements(), page.getTotalElements());
        return page;
    }

    public List<Inspection> getAllInspections() {
        logger.info("📋 Fetching all inspections");
        List<Inspection> inspections = inspectionRepository.findAll();
        logger.info("✅ Found {} inspections", inspections.size());
        return inspections;
    }
}
