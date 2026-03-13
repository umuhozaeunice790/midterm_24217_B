package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.entity.Inspector;
import com.example.BuildingPermitMs.exception.DuplicateResourceException;
import com.example.BuildingPermitMs.repository.InspectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InspectorService {
    private static final Logger logger = LoggerFactory.getLogger(InspectorService.class);
    private final InspectorRepository inspectorRepository;

    public InspectorService(InspectorRepository inspectorRepository) {
        this.inspectorRepository = inspectorRepository;
    }

    public Inspector createInspector(Inspector inspector) {
        logger.info("🔄 Creating inspector: {} with license: {}", inspector.getName(), inspector.getLicenseNumber());
        
        if (inspectorRepository.existsByLicenseNumber(inspector.getLicenseNumber())) {
            logger.error("❌ License number '{}' already exists", inspector.getLicenseNumber());
            throw new DuplicateResourceException("License number '" + inspector.getLicenseNumber() + "' already exists");
        }
        
        Inspector saved = inspectorRepository.save(inspector);
        logger.info("✅ Inspector created successfully with ID: {}", saved.getId());
        return saved;
    }

    public List<Inspector> getAllInspectors() {
        logger.info("📋 Fetching all inspectors");
        List<Inspector> inspectors = inspectorRepository.findAll();
        logger.info("✅ Found {} inspectors", inspectors.size());
        return inspectors;
    }
}
