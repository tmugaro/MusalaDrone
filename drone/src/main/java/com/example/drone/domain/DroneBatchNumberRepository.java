package com.example.drone.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DroneBatchNumberRepository extends JpaRepository<DroneBatchNumber, Long>, JpaSpecificationExecutor<DroneBatchNumber> {

    @Query(value = "select * from DroneBatchNumber d where d.drone.id = :id  ORDER BY d.createdAt asc", nativeQuery = true)
    Optional<DroneBatchNumber> findLatestDroneBatchNumber(Long id);
}