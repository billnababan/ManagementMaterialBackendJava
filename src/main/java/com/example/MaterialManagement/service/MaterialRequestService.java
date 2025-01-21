package com.example.MaterialManagement.service;

import com.example.MaterialManagement.dto.request.MaterialRequestDTO;
import com.example.MaterialManagement.dto.request.RejectRequestDTO;
import com.example.MaterialManagement.entity.MaterialRequest;
import com.example.MaterialManagement.entity.MaterialRequestItem;
import com.example.MaterialManagement.entity.User;
import com.example.MaterialManagement.enums.Department;
import com.example.MaterialManagement.enums.RequestStatus;
import com.example.MaterialManagement.exception.ResourceNotFoundException;
import com.example.MaterialManagement.repository.MaterialRequestRepository;
import com.example.MaterialManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
// import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialRequestService {
    private final MaterialRequestRepository materialRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public MaterialRequest createRequest(MaterialRequestDTO requestDTO) {
        User currentUser = getCurrentUser();
        if (currentUser.getDepartment() != Department.PRODUCTION) {
            throw new IllegalStateException("Only production department can create requests");
        }
    
        MaterialRequest request = new MaterialRequest();
        request.setRequestNumber(generateRequestNumber()); // Generate request number automatically
        request.setRequester(currentUser);
        request.setStatus(RequestStatus.PENDING_APPROVAL);
    
        List<MaterialRequestItem> items = requestDTO.getItems().stream()
                .map(itemDTO -> {
                    MaterialRequestItem item = new MaterialRequestItem();
                    item.setMaterialName(itemDTO.getMaterialName());
                    item.setRequestedQuantity(itemDTO.getRequestedQuantity());
                    item.setUnit(itemDTO.getUnit());
                    item.setUsageDescription(itemDTO.getUsageDescription());
                    item.setMaterialRequest(request);
                    return item;
                })
                .toList();
    
        request.getItems().addAll(items);
        return materialRequestRepository.save(request);
    }
    
    @Transactional
    public MaterialRequest updateRequest(Long requestId, MaterialRequestDTO requestDTO) {
        User currentUser = getCurrentUser();
        if (currentUser.getDepartment() != Department.PRODUCTION) {
            throw new IllegalStateException("Only production department can update requests");
        }
    
        MaterialRequest request = getRequestById(requestId);
        if (request.getStatus() != RequestStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Request cannot be updated because it is already " + request.getStatus());
        }
    
        // Tidak perlu mengubah requestNumber
        // Update items
        List<MaterialRequestItem> updatedItems = requestDTO.getItems().stream()
                .map(itemDTO -> {
                    MaterialRequestItem item = new MaterialRequestItem();
                    item.setMaterialName(itemDTO.getMaterialName());
                    item.setRequestedQuantity(itemDTO.getRequestedQuantity());
                    item.setUnit(itemDTO.getUnit());
                    item.setUsageDescription(itemDTO.getUsageDescription());
                    item.setMaterialRequest(request);
                    return item;
                })
                .toList();
    
        request.getItems().clear(); // Clear existing items
        request.getItems().addAll(updatedItems); // Add updated items
    
        return materialRequestRepository.save(request);
    }

    @Transactional
    public void deleteRequest(Long requestId) {
        User currentUser = getCurrentUser();
        if (currentUser.getDepartment() != Department.PRODUCTION) {
            throw new IllegalStateException("Only production department can delete requests");
        }

        MaterialRequest request = getRequestById(requestId);
        if (request.getStatus() != RequestStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Request cannot be deleted because it is already " + request.getStatus());
        }

        materialRequestRepository.delete(request);
    }

    @Transactional
    public MaterialRequest approveRequest(Long requestId) {
        User approver = getCurrentUser();
        if (approver.getDepartment() != Department.WAREHOUSE) {
            throw new IllegalStateException("Only warehouse department can approve requests");
        }

        MaterialRequest request = getRequestById(requestId);
        if (request.getStatus() != RequestStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Request is already " + request.getStatus());
        }

        request.setStatus(RequestStatus.APPROVED);
        request.setApprovedBy(approver);
        return materialRequestRepository.save(request);
    }

    @Transactional
    public MaterialRequest rejectRequest(Long requestId, RejectRequestDTO rejectDTO) {
        User rejector = getCurrentUser();
        if (rejector.getDepartment() != Department.WAREHOUSE) {
            throw new IllegalStateException("Only warehouse department can reject requests");
        }

        MaterialRequest request = getRequestById(requestId);
        if (request.getStatus() != RequestStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Request is already " + request.getStatus());
        }

        if (rejectDTO.getReason() == null || rejectDTO.getReason().trim().isEmpty()) {
            throw new IllegalArgumentException("Rejection reason is required");
        }

        request.setStatus(RequestStatus.REJECTED);
        request.setApprovedBy(rejector); // Menggunakan approvedBy untuk tracking siapa yang menolak
        request.setRejectionReason(rejectDTO.getReason());

        return materialRequestRepository.save(request);
    }

    public List<MaterialRequest> getAllRequests() {
        return materialRequestRepository.findAll();
    }

    public MaterialRequest getRequestById(Long id) {
        return materialRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + id));
    }

    public List<MaterialRequest> getMyRequests() {
        User currentUser = getCurrentUser();
        return materialRequestRepository.findByRequester(currentUser);
    }

    public List<MaterialRequest> getPendingRequests() {
        return materialRequestRepository.findByStatus(RequestStatus.PENDING_APPROVAL);
    }

    private String generateRequestNumber() {
        return "MR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}