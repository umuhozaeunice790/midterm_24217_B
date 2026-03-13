package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.ApplicantRequest;
import com.example.BuildingPermitMs.entity.Applicant;
import com.example.BuildingPermitMs.entity.Province;
import com.example.BuildingPermitMs.exception.DuplicateResourceException;
import com.example.BuildingPermitMs.exception.ResourceNotFoundException;
import com.example.BuildingPermitMs.repository.ApplicantRepository;
import com.example.BuildingPermitMs.repository.ProvinceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicantService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicantService.class);
    private final ApplicantRepository applicantRepository;
    private final ProvinceRepository provinceRepository;

    public ApplicantService(ApplicantRepository applicantRepository, ProvinceRepository provinceRepository) {
        this.applicantRepository = applicantRepository;
        this.provinceRepository = provinceRepository;
    }

    public Applicant registerApplicant(ApplicantRequest request) {
        logger.info("🔄 Registering applicant: {} with email: {}", request.getName(), request.getEmail());
        
        if (applicantRepository.existsByEmail(request.getEmail())) {
            logger.error("❌ Email '{}' already exists", request.getEmail());
            throw new DuplicateResourceException("Email '" + request.getEmail() + "' already exists");
        }
        
        Province province = provinceRepository.findById(request.getProvinceId())
            .orElseThrow(() -> {
                logger.error("❌ Province with ID {} not found", request.getProvinceId());
                return new ResourceNotFoundException("Province with ID " + request.getProvinceId() + " not found");
            });
        
        Applicant applicant = new Applicant();
        applicant.setName(request.getName());
        applicant.setEmail(request.getEmail());
        applicant.setPhone(request.getPhone());
        applicant.setProvince(province);
        
        Applicant saved = applicantRepository.save(applicant);
        logger.info("✅ Applicant registered successfully with ID: {}", saved.getId());
        return saved;
    }

    public List<Applicant> getApplicantsByProvinceName(String provinceName) {
        logger.info("📋 Fetching applicants by province name: {}", provinceName);
        List<Applicant> applicants = applicantRepository.findByProvinceName(provinceName);
        logger.info("✅ Found {} applicants for province: {}", applicants.size(), provinceName);
        return applicants;
    }

    public List<Applicant> getApplicantsByProvinceCode(String provinceCode) {
        logger.info("📋 Fetching applicants by province code: {}", provinceCode);
        List<Applicant> applicants = applicantRepository.findByProvinceCode(provinceCode);
        logger.info("✅ Found {} applicants for province code: {}", applicants.size(), provinceCode);
        return applicants;
    }

    public Page<Applicant> getAllApplicants(Pageable pageable) {
        logger.info("📋 Fetching applicants with pagination - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Applicant> page = applicantRepository.findAll(pageable);
        logger.info("✅ Retrieved {} applicants out of {} total", page.getNumberOfElements(), page.getTotalElements());
        return page;
    }

    public List<Applicant> getAllApplicants() {
        logger.info("📋 Fetching all applicants");
        List<Applicant> applicants = applicantRepository.findAll();
        logger.info("✅ Found {} applicants", applicants.size());
        return applicants;
    }
}
