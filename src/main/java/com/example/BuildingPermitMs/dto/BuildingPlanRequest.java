package com.example.BuildingPermitMs.dto;

public class BuildingPlanRequest {
    private String fileName;
    private String fileUrl;
    private Long permitId;

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public Long getPermitId() { return permitId; }
    public void setPermitId(Long permitId) { this.permitId = permitId; }
}
