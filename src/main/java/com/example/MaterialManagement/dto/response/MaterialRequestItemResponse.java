// MaterialRequestItemResponse.java
package com.example.MaterialManagement.dto.response;

import com.example.MaterialManagement.entity.MaterialRequestItem;
import lombok.Data;

@Data
public class MaterialRequestItemResponse {
    private Long id;
    private String materialName;
    private Integer requestedQuantity;
    private Integer approvedQuantity;
    private String unit;
    private String usageDescription;

    public static MaterialRequestItemResponse fromEntity(MaterialRequestItem item) {
        MaterialRequestItemResponse response = new MaterialRequestItemResponse();
        response.setId(item.getId());
        response.setMaterialName(item.getMaterialName());
        response.setRequestedQuantity(item.getRequestedQuantity());
        
        response.setUnit(item.getUnit());
        response.setUsageDescription(item.getUsageDescription());
        return response;
    }
}