package com.example.BuildingPermitMs.dto;

import java.util.List;

public class AssignInspectorRequest {
    private List<Long> inspectorIds;

    public List<Long> getInspectorIds() { return inspectorIds; }
    public void setInspectorIds(List<Long> inspectorIds) { this.inspectorIds = inspectorIds; }
}
