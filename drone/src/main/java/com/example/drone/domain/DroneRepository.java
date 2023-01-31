package com.example.drone.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DroneRepository extends JpaRepository<Drone, Long>, JpaSpecificationExecutor<Drone> {
}