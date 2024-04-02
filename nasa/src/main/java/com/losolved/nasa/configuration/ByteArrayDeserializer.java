package com.losolved.nasa.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Base64;

public class ByteArrayDeserializer extends StdDeserializer<byte[]> {

    public ByteArrayDeserializer() {
        this(null);
    }

    protected ByteArrayDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public byte[] deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String base64 = node.asText(); // Assuming the byte array is stored as a Base64 string in JSON
        return Base64.getDecoder().decode(base64);
    }
}

