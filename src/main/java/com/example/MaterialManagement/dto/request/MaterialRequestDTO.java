package com.example.MaterialManagement.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import lombok.Data;
import java.util.List;

@Data
public class MaterialRequestDTO {

    @NotEmpty(message = "Items cannot be empty")
    @Valid
    private List<MaterialRequestItemDTO> items;
}