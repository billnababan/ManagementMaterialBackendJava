package com.example.MaterialManagement.controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.MaterialManagement.dto.request.MaterialRequestDTO;
import com.example.MaterialManagement.dto.request.RejectRequestDTO;
import com.example.MaterialManagement.dto.response.MaterialRequestResponse;
import com.example.MaterialManagement.entity.MaterialRequest;
import com.example.MaterialManagement.service.MaterialRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/material-requests")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class MaterialRequestController {
    private final MaterialRequestService materialRequestService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<MaterialRequestResponse> createRequest(@Valid @RequestBody MaterialRequestDTO requestDTO) {
        MaterialRequest request = materialRequestService.createRequest(requestDTO);
        return ResponseEntity.ok(MaterialRequestResponse.fromEntity(request));
    }

    @GetMapping
    public ResponseEntity<List<MaterialRequestResponse>> getAllRequests() {
        List<MaterialRequestResponse> responses = materialRequestService.getAllRequests()
            .stream()
            .map(MaterialRequestResponse::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialRequestResponse> getRequestById(@PathVariable Long id) {
        MaterialRequest request = materialRequestService.getRequestById(id);
        return ResponseEntity.ok(MaterialRequestResponse.fromEntity(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<MaterialRequestResponse> updateRequest(
            @PathVariable Long id,
            @Valid @RequestBody MaterialRequestDTO requestDTO) {
        MaterialRequest updatedRequest = materialRequestService.updateRequest(id, requestDTO);
        return ResponseEntity.ok(MaterialRequestResponse.fromEntity(updatedRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PRODUCTION')")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        materialRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public ResponseEntity<MaterialRequestResponse> approveRequest(@PathVariable Long id) {
        MaterialRequest request = materialRequestService.approveRequest(id);
        return ResponseEntity.ok(MaterialRequestResponse.fromEntity(request));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public ResponseEntity<MaterialRequestResponse> rejectRequest(
            @PathVariable Long id,
            @Valid @RequestBody RejectRequestDTO rejectDTO) {
        MaterialRequest request = materialRequestService.rejectRequest(id, rejectDTO);
        return ResponseEntity.ok(MaterialRequestResponse.fromEntity(request));
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<MaterialRequestResponse>> getMyRequests() {
        List<MaterialRequestResponse> responses = materialRequestService.getMyRequests()
            .stream()
            .map(MaterialRequestResponse::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public ResponseEntity<List<MaterialRequestResponse>> getPendingRequests() {
        List<MaterialRequestResponse> responses = materialRequestService.getPendingRequests()
            .stream()
            .map(MaterialRequestResponse::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}