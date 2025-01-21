package com.example.MaterialManagement.dto.response;

import com.example.MaterialManagement.enums.Department;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private Department department;
}