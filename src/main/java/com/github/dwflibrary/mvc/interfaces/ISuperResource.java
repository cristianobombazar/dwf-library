package com.github.dwflibrary.mvc.interfaces;

import com.github.dwflibrary.util.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ISuperResource {

    default ObjectMapper getObjectMapper(){
        return ObjectMapperUtil.getInstance();
    }

    default ObjectMapper getObjectMapperSquiggly(String fields){
        return ObjectMapperUtil.getInstanceSquiggly(fields);
    }
}
