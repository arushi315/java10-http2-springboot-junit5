/*
 * Project GreenBox
 * (c) 2015-2018 VMware, Inc. All rights reserved.
 * VMware Confidential.
 */

package com.nischit.sample.myservice.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final TypeFactory TYPE_FACTORY = defaultInstance();

    private JsonUtils() {
    }

    public static byte[] convertToJsonAsBytes(final Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(obj);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static String convertToJson(final Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static <T> T convertFromJson(final String jsonData, final Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, type);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static <T> T convertFromJsonBytes(final byte[] jsonData, final Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, type);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static <K, V> Map<K, V> convertToMap(final byte[] jsonData, Class<K> key, Class<V> value) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, TYPE_FACTORY.constructMapType(Map.class, key, value));
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static <K, V> Map<K, V> convertJsonNodeToMap(final JsonNode jsonNode, Class<K> key, Class<V> value) {
        try {
            byte[] jsonData = OBJECT_MAPPER.writeValueAsBytes(jsonNode);
            return OBJECT_MAPPER.readValue(jsonData, TYPE_FACTORY.constructMapType(Map.class, key, value));
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static <T> Collection<T> convertFromJsonToCollection(final String jsonData, Class<? extends Collection> collectionType,
                                                                Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, TYPE_FACTORY.constructCollectionType(collectionType, type));
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static <T> List<T> convertFromJsonToList(final String jsonData, Class<? extends List> listType,
                                                    Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, TYPE_FACTORY.constructCollectionType(listType, type));
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static JsonNode createJsonNode(final String fileName) {
        try (final InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(fileName)) {
            return OBJECT_MAPPER.readTree(inputStream);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static JsonNode createJsonNode(final File filePath) {
        try (final InputStream inputStream = new FileInputStream(filePath)) {
            return OBJECT_MAPPER.readTree(inputStream);
        } catch (IOException e) {
            throw new SerializerException(e);
        }
    }

    public static String convertFileToJsonString(final String fileName) {
        byte[] input = new byte[100];
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(final InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            while((bytesRead = inputStream.read(input)) != -1){
                outputStream.write(input, 0, bytesRead);
            }
        } catch (IOException ex) {
            throw new SerializerException(ex);
        }
        return outputStream.toString();
    }
}
