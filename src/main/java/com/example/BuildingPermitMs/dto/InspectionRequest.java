package com.example.BuildingPermitMs.dto;

public class InspectionRequest {
    private Long permitId;
    private Long inspectorId;
    private String result;
    private String remarks;

    public Long getPermitId() { return permitId; }
    public void setPermitId(Long permitId) { this.permitId = permitId; }
    public Long getInspectorId() { return inspectorId; }
    public void setInspectorId(Long inspectorId) { this.inspectorId = inspectorId; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
