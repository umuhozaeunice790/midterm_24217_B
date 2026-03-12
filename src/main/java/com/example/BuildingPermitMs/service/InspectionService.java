package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.InspectionRequest;
import com.example.BuildingPermitMs.entity.BuildingPermit;
import com.example.BuildingPermitMs.entity.Inspection;
import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.repository.BuildingPermitRepository;
import com.example.BuildingPermitMs.repository.InspectionRepository;
import com.example.BuildingPermitMs.repository.InspectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InspectionService {
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
        BuildingPermit permit = permitRepository.findById(request.getPermitId())
            .orElseThrow(() -> new RuntimeException("Permit not found"));
        Inspector inspector = inspectorRepository.findById(request.getInspectorId())
            .orElseThrow(() -> new RuntimeException("Inspector not found"));
        
        Inspection inspection = new Inspection();
        inspection.setBuildingPermit(permit);
        inspection.setInspector(inspector);
        inspection.setResult(Inspection.InspectionResult.valueOf(request.getResult()));
        inspection.setRemarks(request.getRemarks());
        
        Inspection saved = inspectionRepository.save(inspection);
        
        if (inspection.getResult() == Inspection.InspectionResult.PASSED) {
            permit.setStatus(BuildingPermit.PermitStatus.APPROVED);
            permitRepository.save(permit);
        }
        
        return saved;
    }

    public Page<Inspection> getAllInspections(Pageable pageable) {
        return inspectionRepository.findAll(pageable);
    }

    public List<Inspection> getAllInspections() {
        return inspectionRepository.findAll();
    }
}
