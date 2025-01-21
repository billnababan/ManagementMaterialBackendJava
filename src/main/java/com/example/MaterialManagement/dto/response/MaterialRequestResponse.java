// MaterialRequestResponse.java
package com.example.MaterialManagement.dto.response;

import com.example.MaterialManagement.entity.MaterialRequest;
import com.example.MaterialManagement.enums.RequestStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MaterialRequestResponse {
    private Long id;
    private String requestNumber;
    private String requesterUsername;
    private String approverUsername;
    private RequestStatus status;
    private String rejectionReason;
    private List<MaterialRequestItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MaterialRequestResponse fromEntity(MaterialRequest request) {
        MaterialRequestResponse response = new MaterialRequestResponse();
        response.setId(request.getId());
        response.setRequestNumber(request.getRequestNumber());
        response.setRequesterUsername(request.getRequester().getUsername());
        if (request.getApprovedBy() != null) {
            response.setApproverUsername(request.getApprovedBy().getUsername());
        }
        response.setStatus(request.getStatus());
        response.setRejectionReason(request.getRejectionReason());
        response.setCreatedAt(request.getCreatedAt());
        response.setUpdatedAt(request.getUpdatedAt());

        List<MaterialRequestItemResponse> itemResponses = request.getItems().stream()
            .map(MaterialRequestItemResponse::fromEntity)
            .collect(Collectors.toList());
        response.setItems(itemResponses);

        return response;
    }
}