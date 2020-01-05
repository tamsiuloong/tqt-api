package com.coachtam.tqt.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-04 21:01
 * @Description:
 */
public class StringSerializer extends JsonSerializer {
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(o.toString());
    }
}
