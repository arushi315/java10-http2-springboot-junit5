package com.nischit.sample.myservice.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JsonUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void convertToJson() {
        Map<String, String> obj = new HashMap<>();
        obj.put("key", "value");
        assertNotNull(JsonUtils.convertToJson(obj));
    }

    @Test
    void convertFileToJsonString() {
        assertNotNull(JsonUtils.convertFileToJsonString("teamDet.json"));
    }
}