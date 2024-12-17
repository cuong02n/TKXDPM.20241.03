package com.cuong02n.aimsbackend.converter;

import jakarta.persistence.AttributeConverter;

import java.util.HashMap;

import static com.cuong02n.aimsbackend.util.GsonUtil.fromJson;
import static com.cuong02n.aimsbackend.util.GsonUtil.toJson;

public class ProductHashMapConverter implements AttributeConverter<HashMap<String,String>, String> {
    @Override
    public String convertToDatabaseColumn(HashMap<String,String> attribute) {
        return toJson(attribute);
    }

    @Override
    public HashMap<String,String> convertToEntityAttribute(String dbData) {
        return fromJson(dbData, HashMap.class);
    }
}
