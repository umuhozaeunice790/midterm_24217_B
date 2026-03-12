package com.example.BuildingPermitMs.service;

import com.example.BuildingPermitMs.entity.Province;
import com.example.BuildingPermitMs.repository.ProvinceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Province createProvince(Province province) {
        if (provinceRepository.existsByCode(province.getCode())) {
            throw new RuntimeException("Province code already exists");
        }
        return provinceRepository.save(province);
    }

    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }
}
