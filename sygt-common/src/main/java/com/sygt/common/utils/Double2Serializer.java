package com.sygt.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author cm
 * @version 1.0
 * @description: TODO
 * @date 2021/10/8 9:49
 */
public class Double2Serializer extends JsonSerializer<Double> {
    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * 小数保留2位返回给前端序列化器
     * @param data
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Double data, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (data != null) {
            if (data == 0) {
                jsonGenerator.writeString("0");
            } else {
                jsonGenerator.writeString(df.format(data));
            }

        }
    }

}
