package com.example.BuildingPermitMs.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "building_plans")
public class BuildingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    private String fileUrl;

    private LocalDateTime uploadDate = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "permit_id", nullable = false)
    private BuildingPermit buildingPermit;

    public BuildingPlan() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }
    public BuildingPermit getBuildingPermit() { return buildingPermit; }
    public void setBuildingPermit(BuildingPermit buildingPermit) { this.buildingPermit = buildingPermit; }
}
