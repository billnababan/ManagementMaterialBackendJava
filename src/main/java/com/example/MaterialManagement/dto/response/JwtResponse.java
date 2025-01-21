package com.example.MaterialManagement.dto.response;

import com.example.MaterialManagement.enums.Department;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private String token;
    
    @Builder.Default
    private String type = "Bearer";
    private Long id;
    private String username;
    private Department department;
}