package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.entity.Province;
import com.example.BuildingPermitMs.service.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceController.class);
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping
    public ResponseEntity<Province> createProvince(@RequestBody Province province) {
        logger.info("📥 POST /api/provinces - Creating province: {}", province.getCode());
        Province created = provinceService.createProvince(province);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Province>> getAllProvinces() {
        logger.info("📥 GET /api/provinces - Fetching all provinces");
        return ResponseEntity.ok(provinceService.getAllProvinces());
    }
}
