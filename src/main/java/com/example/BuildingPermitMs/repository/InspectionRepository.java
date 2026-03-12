package com.example.BuildingPermitMs.repository;

import com.example.BuildingPermitMs.entity.Inspection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    List<Inspection> findByBuildingPermitId(Long permitId);
    Page<Inspection> findAll(Pageable pageable);
}
