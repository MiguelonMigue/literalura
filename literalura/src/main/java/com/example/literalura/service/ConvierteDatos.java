package com.example.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvierteDatos {
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public <T> T obtenerDatos(String json, Class<T> clase) {
//        try {
//            return objectMapper.readValue(json,clase);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public <T> T desdeJson(String json, Class<T> clase) {
//        try {
//            return objectMapper.readValue(json, clase);
//        } catch (Exception e) {
//            throw new RuntimeException("Error al convertir JSON", e);
//        }
//    }
private final ObjectMapper mapper;

    public ConvierteDatos() {
        this.mapper = new ObjectMapper();
        // Ignora propiedades desconocidas si no tienes @JsonIgnoreProperties
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T desdeJson(String json, Class<T> clase) {
        try {
            return mapper.readValue(json, clase);
        } catch (Exception e) {
            System.out.println("❌ Error al convertir JSON: " + e.getMessage());
            return null;
        }
    }

}
