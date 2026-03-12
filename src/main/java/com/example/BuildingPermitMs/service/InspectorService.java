package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.repository.InspectorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InspectorService {
    private final InspectorRepository inspectorRepository;

    public InspectorService(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    public Inspector createInspector(Inspector inspector) {
        if (inspectorRepository.existsByLicenseNumber(inspector.getLicenseNumber())) {
            throw new RuntimeException("License number already exists");
        }
        return inspectorRepository.save(inspector);
    }

    public List<Inspector> getAllInspectors() {
        return inspectorRepository.findAll();
    }
}
