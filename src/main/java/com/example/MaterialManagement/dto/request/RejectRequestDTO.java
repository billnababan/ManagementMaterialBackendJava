package com.example.MaterialManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RejectRequestDTO {
    @NotBlank(message = "Rejection reason is required")
    private String reason;
}