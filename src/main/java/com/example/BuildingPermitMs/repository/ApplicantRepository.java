package com.example.BuildingPermitMs.repository;

import com.example.BuildingPermitMs.entity.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    boolean existsByEmail(String email);
    List<Applicant> findByProvinceName(String provinceName);
    List<Applicant> findByProvinceCode(String provinceCode);
    Page<Applicant> findAll(Pageable pageable);
}
