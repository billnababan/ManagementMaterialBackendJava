package com.example.MaterialManagement.entity;

import com.example.MaterialManagement.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "material_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String requestNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING_APPROVAL;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @JsonManagedReference
    @OneToMany(mappedBy = "materialRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MaterialRequestItem> items = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper method untuk menambah item
    public void addItem(MaterialRequestItem item) {
        items.add(item);
        item.setMaterialRequest(this);
    }

    // Helper method untuk menghapus item
    public void removeItem(MaterialRequestItem item) {
        items.remove(item);
        item.setMaterialRequest(null);
    }
}