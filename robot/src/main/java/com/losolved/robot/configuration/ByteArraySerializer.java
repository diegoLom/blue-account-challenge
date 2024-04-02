package com.losolved.robot.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class ByteArraySerializer extends JsonSerializer<byte[]> {
    @Override
    public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Start array
        gen.writeStartArray();
        // Write each byte to the array
        for (byte b : value) {
            gen.writeNumber(b & 0xFF); // Ensure that bytes are treated as unsigned
        }
        // End array
        gen.writeEndArray();
    }
}

