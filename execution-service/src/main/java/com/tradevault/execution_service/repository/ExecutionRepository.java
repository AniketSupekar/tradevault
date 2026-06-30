package com.tradevault.execution_service.repository;

import com.tradevault.execution_service.entity.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExecutionRepository extends JpaRepository<Execution, UUID> {
}