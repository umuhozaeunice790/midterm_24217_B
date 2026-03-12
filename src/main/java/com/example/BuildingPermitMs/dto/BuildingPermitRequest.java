package com.example.BuildingPermitMs.dto;

public class BuildingPermitRequest {
    private String projectName;
    private String location;
    private Long applicantId;

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }
}
