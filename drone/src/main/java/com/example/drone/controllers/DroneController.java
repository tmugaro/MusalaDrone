package com.example.drone.controllers;


import com.example.drone.domain.Drone;
import com.example.drone.domain.DroneBatchNumber;
import com.example.drone.domain.Medication;
import com.example.drone.dto.BatteryDto;
import com.example.drone.dto.DroneDto;
import com.example.drone.dto.DroneItems;
import com.example.drone.service.DroneService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DroneController {
    private final DroneService service;

    public DroneController(DroneService service) {
        this.service = service;
    }

    @PostMapping(value = "/drones",
            consumes = {MediaType.APPLICATION_JSON_VALUE },
            produces =  {MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Drone> registeringDrone(@RequestBody DroneDto droneDto) {
       return ResponseEntity.ok(service.registeringDrone(droneDto));
    }

    @PostMapping(value = "/drones/{droneId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE },
            produces =  {MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<DroneBatchNumber> loadDroneWithMedialItems(@PathVariable  Long droneId, @RequestBody DroneItems droneItems) {
        return ResponseEntity.ok(service.loadDroneWithMedialItems(droneId, droneItems));
    }

    @GetMapping(value = "/drones",   produces =  {MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity<List<Drone>> checkingAvailableDronesForLoading() {
        return ResponseEntity.ok(service.checkingAvailableDronesForLoading());
    }
    @GetMapping(value = "/drones/{droneId}",produces =  {MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity<BatteryDto> checkDroneBatteryLevel(@PathVariable Long droneId){
        return  ResponseEntity.ok(service.checkDroneBatteryLevel(droneId));
    }
    @GetMapping(value = "/drones/{droneId}/medicines",produces =  {MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity<List<Medication>> checkingLoadedMedicationItems(@PathVariable Long droneId){
        return ResponseEntity.ok(service.checkingLoadedMedicationItems(droneId));
    }
}
