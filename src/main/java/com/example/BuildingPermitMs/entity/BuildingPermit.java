package com.example.BuildingPermitMs.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "building_permits")
public class BuildingPermit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectName;

    private String location;

    @Enumerated(EnumType.STRING)
    private PermitStatus status = PermitStatus.PENDING;

    private LocalDate submissionDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @OneToOne(mappedBy = "buildingPermit", cascade = CascadeType.ALL)
    private BuildingPlan buildingPlan;

    @ManyToMany
    @JoinTable(
        name = "permit_inspectors",
        joinColumns = @JoinColumn(name = "permit_id"),
        inverseJoinColumns = @JoinColumn(name = "inspector_id")
    )
    private List<Inspector> inspectors;

    @OneToMany(mappedBy = "buildingPermit", cascade = CascadeType.ALL)
    private List<Inspection> inspections;

    public enum PermitStatus {
        PENDING, APPROVED, REJECTED, UNDER_INSPECTION
    }

    public BuildingPermit() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public PermitStatus getStatus() { return status; }
    public void setStatus(PermitStatus status) { this.status = status; }
    public LocalDate getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDate submissionDate) { this.submissionDate = submissionDate; }
    public Applicant getApplicant() { return applicant; }
    public void setApplicant(Applicant applicant) { this.applicant = applicant; }
    public BuildingPlan getBuildingPlan() { return buildingPlan; }
    public void setBuildingPlan(BuildingPlan buildingPlan) { this.buildingPlan = buildingPlan; }
    public List<Inspector> getInspectors() { return inspectors; }
    public void setInspectors(List<Inspector> inspectors) { this.inspectors = inspectors; }
    public List<Inspection> getInspections() { return inspections; }
    public void setInspections(List<Inspection> inspections) { this.inspections = inspections; }
}
