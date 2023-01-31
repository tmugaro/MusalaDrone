package com.example.drone.dto;

public class BatteryDto {
    private final Integer level;

    public BatteryDto(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
