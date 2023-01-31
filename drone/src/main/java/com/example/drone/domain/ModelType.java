package com.example.drone.domain;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

public enum ModelType {
    LIGHT("Lightweight"),
    MIDDLE("Middleweight"),
    CRUISER("Cruiserweight"),
    HEAVY("Heavyweight");
    private final String value;
    private static final Map<String, ModelType> _CONSTANTS = new HashMap<>();

    static  {
        for (ModelType v : values()) {
            _CONSTANTS.put(v.value, v);
        }
    }

    ModelType(String value) {
        this.value = value;
    }


    @Converter
    public static class ModelTypeConverter implements AttributeConverter<ModelType, String>{
        @Override
        public String convertToDatabaseColumn(ModelType attribute) {
            if(attribute != null){
                return attribute.value;
            }
            return null;
        }

        @Override
        public ModelType convertToEntityAttribute(String dbData) {
            return _CONSTANTS.get(dbData);
        }
    }



}
