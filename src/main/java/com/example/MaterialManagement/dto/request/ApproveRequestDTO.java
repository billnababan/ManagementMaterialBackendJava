package com.example.MaterialManagement.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class ApproveRequestDTO {
    @NotEmpty(message = "Items cannot be empty")
    @Valid
    private List<ApprovedItemDTO> items;
}