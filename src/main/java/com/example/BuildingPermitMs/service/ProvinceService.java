package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.entity.Province;
import com.example.BuildingPermitMs.exception.DuplicateResourceException;
import com.example.BuildingPermitMs.repository.ProvinceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProvinceService {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceService.class);
    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Province createProvince(Province province) {
        logger.info("🔄 Creating province with code: {} and name: {}", province.getCode(), province.getName());
        
        if (provinceRepository.existsByCode(province.getCode())) {
            logger.error("❌ Province code '{}' already exists", province.getCode());
            throw new DuplicateResourceException("Province code '" + province.getCode() + "' already exists");
        }
        
        Province saved = provinceRepository.save(province);
        logger.info("✅ Province created successfully with ID: {}", saved.getId());
        return saved;
    }

    public List<Province> getAllProvinces() {
        logger.info("📋 Fetching all provinces");
        List<Province> provinces = provinceRepository.findAll();
        logger.info("✅ Found {} provinces", provinces.size());
        return provinces;
    }
}
