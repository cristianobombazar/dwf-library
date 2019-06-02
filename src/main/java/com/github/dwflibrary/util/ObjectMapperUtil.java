package com.github.dwflibrary.util;


import com.github.dwflibrary.spring.ApplicationContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;

import java.io.IOException;

public class ObjectMapperUtil {

    public static String toString(ObjectMapper objectMapper, Object object){
        try {
            return objectMapper.writeValueAsString(object);
        }catch (IOException ignored){}
        return null;
    }

    public static <E> E toObject(ObjectMapper objectMapper, String value, Class<E> resultClass) {
        try {
            return objectMapper.readValue(value, resultClass);
        } catch (IOException ignored) {}
        return null;
    }

    public static ObjectMapper getInstance() {
        return ApplicationContextProvider.getBean(ObjectMapper.class);
    }

    public static ObjectMapper getInstanceSquiggly(String fields) {
        ObjectMapper original = getInstance();
        ObjectMapper target   = original.copy();
        return Squiggly.init(target, fields);
    }
}
