package com.example.MaterialManagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApprovedItemDTO {
    @NotNull(message = "Item ID cannot be null")
    private Long itemId;
    
    @NotNull(message = "Approved quantity cannot be null")
    @Min(value = 0, message = "Approved quantity cannot be negative")
    private Integer approvedQuantity;
}