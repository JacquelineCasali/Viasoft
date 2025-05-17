package com.viasoft.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String serialize(Object obj) throws JsonProcessingException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
