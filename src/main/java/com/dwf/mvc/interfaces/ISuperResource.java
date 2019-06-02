package com.dwf.mvc.interfaces;

import com.dwf.util.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ISuperResource {

    default ObjectMapper getObjectMapper(){
        return ObjectMapperUtil.getInstance();
    }

    default ObjectMapper getObjectMapperSquiggly(String fields){
        return ObjectMapperUtil.getInstanceSquiggly(fields);
    }
}
