package com.example.MaterialManagement.repository;

import com.example.MaterialManagement.entity.MaterialRequest;
import com.example.MaterialManagement.entity.User;
import com.example.MaterialManagement.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRequestRepository extends JpaRepository<MaterialRequest, Long> {
    List<MaterialRequest> findByRequester(User requester);
    List<MaterialRequest> findByStatus(RequestStatus status);
}