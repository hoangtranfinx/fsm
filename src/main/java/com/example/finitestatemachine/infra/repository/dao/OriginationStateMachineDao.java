package com.example.finitestatemachine.infra.repository.dao;

import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginationStateMachineDao extends JpaRepository<OriginationStateMachineEntity, Integer> {

    Optional<OriginationStateMachineEntity> findById(int id);
}
