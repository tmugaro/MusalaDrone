package com.example.drone.dto;

import java.util.List;

public class DroneItems {
    private List<MedicationDto> items;

    public List<MedicationDto> getItems() {
        return items;
    }

    public void setItems(List<MedicationDto> items) {
        this.items = items;
    }
}
