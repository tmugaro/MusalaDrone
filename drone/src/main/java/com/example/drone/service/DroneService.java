package com.example.drone.service;

import am.ik.yavi.core.ConstraintViolationsException;
import com.example.drone.domain.*;
import com.example.drone.dto.BatteryDto;
import com.example.drone.dto.DroneDto;
import com.example.drone.dto.DroneItems;
import com.example.drone.validators.Validators;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service(value = "DroneService")
public class DroneService {
    private final Mappers mappers;
    private final DroneRepository droneRepository;
    private final DroneBatchNumberRepository droneBatchNumberRepository;
    private final MedicationRepository medicationRepository;
    private final DroneBatchItemRepository droneBatchItemRepository;


    public DroneService(Mappers mappers, DroneRepository droneRepository, DroneBatchNumberRepository droneBatchNumberRepository, MedicationRepository medicationRepository, DroneBatchItemRepository droneBatchItemRepository) {
        this.mappers = mappers;
        this.droneRepository = droneRepository;
        this.droneBatchNumberRepository = droneBatchNumberRepository;
        this.medicationRepository = medicationRepository;
        this.droneBatchItemRepository = droneBatchItemRepository;
    }

    public Drone registeringDrone(DroneDto droneDto) {
        Objects.requireNonNull(droneDto, "DroneDto must not be null");
        var violations = Validators.droneValidator.validate(droneDto);
        if (violations.isEmpty()) {
            droneDto.setState(DroneState.IDLE);
            return droneRepository.save(mappers.convertToDrone(droneDto));
        }
        throw new ConstraintViolationsException(violations.violations());
    }


    public DroneBatchNumber loadDroneWithMedialItems(Long droneId, DroneItems droneItems) {
        Objects.requireNonNull(droneItems);
        var violations = Validators.itemsValidator.validate(droneItems);
        if (violations.isEmpty()) {
            var drone = droneRepository.findById(droneId).orElseThrow(() -> new EntityNotFoundException("Drone [id=" + droneId + "] not found"));
            if (drone.getState() == DroneState.IDLE && drone.getBatteryLevel() > 25) {
                drone.setState(DroneState.LOADING);
                droneRepository.save(drone);

                var droneBatchNumber = new DroneBatchNumber();
                droneBatchNumber.setDrone(drone);
                droneBatchNumber.setCreatedAt(Instant.now());
                droneBatchNumber = droneBatchNumberRepository.save(droneBatchNumber);

                List<Medication> medications = new ArrayList<>();

                for (var item : droneItems.getItems()) {
                    medications.add(mappers.convertToMedication(item));
                }

                medications = medicationRepository.saveAll(medications);

                List<DroneBatchItem> items = new ArrayList<>();

                for (Medication medication : medications) {
                    DroneBatchItem batchItem = new DroneBatchItem();
                    batchItem.setBatchNumber(droneBatchNumber);
                    batchItem.setMedication(medication);
                    items.add(batchItem);
                }
                droneBatchItemRepository.saveAll(items);


                drone.setState(DroneState.LOADED);
                droneRepository.save(drone);

                return droneBatchNumber;
            }
            throw new IllegalArgumentException("Drone[id=" + droneId + "] must be idle and battery greater than 25%");
        }
        throw new ConstraintViolationsException(violations.violations());
    }

    public List<Drone> checkingAvailableDronesForLoading(){
      return droneRepository.findAll(new Specification<Drone>() {
        @Override
        public Predicate toPredicate(Root<Drone> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
          return criteriaBuilder.equal(root.get(Drone_.STATE), DroneState.IDLE);
        }
      });
    }

   public BatteryDto checkDroneBatteryLevel(Long droneId){
      return   new BatteryDto(droneRepository.findById(droneId)
              .orElseThrow(()-> new EntityNotFoundException("Drone[id="+ droneId +"] not found")).getBatteryLevel());
    }

   public List<Medication> checkingLoadedMedicationItems(Long droneId){
       var drone =   droneRepository.findById(droneId).orElseThrow(()-> new EntityNotFoundException("Drone[id="+ droneId +"] not found"));
       var droneBatchNumber  = droneBatchNumberRepository.findLatestDroneBatchNumber(drone.getId()).orElseThrow(()-> new EntityNotFoundException("Drone[id="+ droneId +"] batch number not found"));
       List<Medication> list = new ArrayList<>();
       for (var s : droneBatchItemRepository.findAll(new Specification<>() {
           @Override
           public Predicate toPredicate(Root<DroneBatchItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
               return criteriaBuilder.equal(root.get(DroneBatchItem_.batchNumber).get(DroneBatchNumber_.ID), droneBatchNumber.getId());
           }
       })) {
           Medication medication = s.getMedication();
           list.add(medication);
       }
       return list;
   }
}
