package com.example.drone.validators;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Constraint;
import am.ik.yavi.core.Validator;
import com.example.drone.dto.DroneDto;
import com.example.drone.dto.DroneItems;
import com.example.drone.dto.MedicationDto;

import java.util.regex.Pattern;


public interface Validators {
    Validator<DroneDto> droneValidator = new ValidatorBuilder<DroneDto>()
            ._integer(DroneDto::getWeight, "weight", c -> c.lessThanOrEqual(500).greaterThan(0))
            ._object(DroneDto::getType, "type", Constraint::notNull)
            ._object(DroneDto::getState, "state", Constraint::notNull)
            ._integer(DroneDto::getBatteryLevel, "batteryLevel", c -> c.lessThanOrEqual(100).greaterThanOrEqual(0))
            ._charSequence(DroneDto::getSerialNumber, "serialNumber", c -> c.lessThanOrEqual(100).greaterThanOrEqual(0))
            .build();
    Validator<MedicationDto> medicationValidator = new ValidatorBuilder<MedicationDto>()
            ._integer(MedicationDto::getWeight, "weight", c -> c.lessThanOrEqual(1000).greaterThan(0))
            ._charSequence(MedicationDto::getName, "type", c -> c.notBlank().pattern(Pattern.compile("[a-zA-Z-_]+")))
            ._charSequence(MedicationDto::getCode, "code", c -> c.notBlank().pattern(Pattern.compile("[a-zA-Z_0-9]+")))
            ._charSequence(MedicationDto::getImage, "image", c -> c.notBlank().url())
            .build();


   Validator<DroneItems> itemsValidator = new ValidatorBuilder<DroneItems>()
           ._collection(DroneItems::getItems, "weight", c ->
                   c.lessThanOrEqual(5)
                           .greaterThan(0))
           .forEach(DroneItems::getItems, "items", medicationValidator)
           .build();



}
