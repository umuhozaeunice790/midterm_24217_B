package com.example.BuildingPermitMs.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inspections")
public class Inspection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate inspectionDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private InspectionResult result;

    @Column(length = 1000)
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "permit_id", nullable = false)
    private BuildingPermit buildingPermit;

    @ManyToOne
    @JoinColumn(name = "inspector_id", nullable = false)
    private Inspector inspector;

    public enum InspectionResult {
        PASSED, FAILED, PENDING
    }

    public Inspection() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getInspectionDate() { return inspectionDate; }
    public void setInspectionDate(LocalDate inspectionDate) { this.inspectionDate = inspectionDate; }
    public InspectionResult getResult() { return result; }
    public void setResult(InspectionResult result) { this.result = result; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public BuildingPermit getBuildingPermit() { return buildingPermit; }
    public void setBuildingPermit(BuildingPermit buildingPermit) { this.buildingPermit = buildingPermit; }
    public Inspector getInspector() { return inspector; }
    public void setInspector(Inspector inspector) { this.inspector = inspector; }
}
