package com.example.BuildingPermitMs.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "inspectors")
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    private String specialization;

    @ManyToMany(mappedBy = "inspectors")
    private List<BuildingPermit> buildingPermits;

    @OneToMany(mappedBy = "inspector", cascade = CascadeType.ALL)
    private List<Inspection> inspections;

    public Inspector() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public List<BuildingPermit> getBuildingPermits() { return buildingPermits; }
    public void setBuildingPermits(List<BuildingPermit> buildingPermits) { this.buildingPermits = buildingPermits; }
    public List<Inspection> getInspections() { return inspections; }
    public void setInspections(List<Inspection> inspections) { this.inspections = inspections; }
}
