package com.example.MaterialManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class MaterialRequestItemDTO {
    @NotBlank(message = "Material name is required")
    private String materialName;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer requestedQuantity;
    
    @NotBlank(message = "Unit is required")
    private String unit;
    
    private String usageDescription;
}