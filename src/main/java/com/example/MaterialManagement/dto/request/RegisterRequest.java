package com.example.MaterialManagement.dto.request;

import com.example.MaterialManagement.enums.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    @NotNull
    private Department department;
}