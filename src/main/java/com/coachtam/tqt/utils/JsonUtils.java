package com.coachtam.tqt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-05 13:43
 * @Description:
 */
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static String toJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json=null;
        try {
             json = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转json异常",e.getCause());
        }

        return json;
    }



    public static <T> List<T> toJsonListObject(String json, Class<T> valueType) {
        try {
            JavaType getCollectionType = MAPPER.getTypeFactory().constructParametricType(List.class, valueType);
            return (List<T>) MAPPER.readValue(json, getCollectionType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toJsonObject(String json, Class<T> valueType) {
        try {
            return MAPPER.<T>readValue(json, valueType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> String toJsonStr(T o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
