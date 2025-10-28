package br.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromObject(Object obj, Class<T> clazz) {
        try {
            return mapper.convertValue(obj, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter objeto para classe " + clazz.getSimpleName(), e);
        }
    }

}
