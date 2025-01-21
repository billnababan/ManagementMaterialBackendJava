package com.example.MaterialManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "material_request_items")
@Data
public class MaterialRequestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_request_id")
    private MaterialRequest materialRequest;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "requested_quantity")
    private Integer requestedQuantity;


    @Column(name = "unit")
    private String unit;

    @Column(name = "usage_description")
    private String usageDescription;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}