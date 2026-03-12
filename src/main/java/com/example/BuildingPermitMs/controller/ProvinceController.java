package com.example.BuildingPermitMs.controller;

import com.example.BuildingPermitMs.entity.Province;
import com.example.BuildingPermitMs.service.ProvinceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping
    public ResponseEntity<Province> createProvince(@RequestBody Province province) {
        return ResponseEntity.ok(provinceService.createProvince(province));
    }

    @GetMapping
    public ResponseEntity<List<Province>> getAllProvinces() {
        return ResponseEntity.ok(provinceService.getAllProvinces());
    }
}
