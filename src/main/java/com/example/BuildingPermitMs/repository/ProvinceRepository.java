package com.example.BuildingPermitMs.repository;

import com.example.BuildingPermitMs.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    boolean existsByCode(String code);
    boolean existsByName(String name);
    Optional<Province> findByCode(String code);
    Optional<Province> findByName(String name);
}
