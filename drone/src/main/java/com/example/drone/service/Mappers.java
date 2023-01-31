package com.example.drone.service;

import com.example.drone.domain.Drone;
import com.example.drone.domain.Medication;
import com.example.drone.dto.DroneDto;
import com.example.drone.dto.MedicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(injectionStrategy =CONSTRUCTOR,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface Mappers {
    Drone convertToDrone(DroneDto droneDto);
    Medication convertToMedication(MedicationDto medicationDto);
}
