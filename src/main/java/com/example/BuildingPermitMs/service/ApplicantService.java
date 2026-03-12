package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.dto.ApplicantRequest;
import com.example.BuildingPermitMs.entity.Applicant;
import com.example.BuildingPermitMs.entity.Province;
import com.example.BuildingPermitMs.repository.ApplicantRepository;
import com.example.BuildingPermitMs.repository.ProvinceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final ProvinceRepository provinceRepository;

    public ApplicantService(ApplicantRepository applicantRepository, ProvinceRepository provinceRepository) {
        this.applicantRepository = applicantRepository;
        this.provinceRepository = provinceRepository;
    }

    public Applicant registerApplicant(ApplicantRequest request) {
        if (applicantRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Province province = provinceRepository.findById(request.getProvinceId())
            .orElseThrow(() -> new RuntimeException("Province not found"));
        
        Applicant applicant = new Applicant();
        applicant.setName(request.getName());
        applicant.setEmail(request.getEmail());
        applicant.setPhone(request.getPhone());
        applicant.setProvince(province);
        return applicantRepository.save(applicant);
    }

    public List<Applicant> getApplicantsByProvinceName(String provinceName) {
        return applicantRepository.findByProvinceName(provinceName);
    }

    public List<Applicant> getApplicantsByProvinceCode(String provinceCode) {
        return applicantRepository.findByProvinceCode(provinceCode);
    }

    public Page<Applicant> getAllApplicants(Pageable pageable) {
        return applicantRepository.findAll(pageable);
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }
}
