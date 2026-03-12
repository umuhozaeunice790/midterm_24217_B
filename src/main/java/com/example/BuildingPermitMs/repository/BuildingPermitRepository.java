package com.example.BuildingPermitMs.repository;

import com.example.BuildingPermitMs.entity.BuildingPermit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BuildingPermitRepository extends JpaRepository<BuildingPermit, Long> {
    List<BuildingPermit> findByApplicantId(Long applicantId);
    Page<BuildingPermit> findAll(Pageable pageable);
    List<BuildingPermit> findByStatus(BuildingPermit.PermitStatus status);
}
