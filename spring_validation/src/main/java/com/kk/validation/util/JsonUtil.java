package com.kk.validation.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author luokexiong
 * @version 1.0 2020/12/24
 * @since 1.0.0
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final TypeReference<Map<String, Object>> OBJECT_TO_STRING_MAP_REF = new TypeReference<Map<String, Object>>() {
    };
    private static final TypeReference<List<Map<String, Object>>> OBJECT_TO_LIST_STRING_MAP_REF = new TypeReference<List<Map<String, Object>>>() {
    };

    private static final ObjectMapper MAPPER;
    private static final ObjectMapper IGNORE_NULL_MAPPER;

    static {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        // 容忍json中出现未知的列
        jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 如果解析对象无法设置json属性时，打开此配置
        /*
        jsonObjectMapper.registerModule(new SimpleModule().addSerializer(
            new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        ).addDeserializer(
            LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ));
        */
        jsonObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);
        MAPPER = jsonObjectMapper;

        IGNORE_NULL_MAPPER = new ObjectMapper();
        // 容忍json中出现未知的列
        IGNORE_NULL_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // ignore the null fields globally
        IGNORE_NULL_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private JsonUtil() {}

    public static <T> Optional<String> serialize(T object) {
        if (null == object) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            LOGGER.error("Serialize to json occurs exception", e);
        }
        return Optional.empty();
    }

    /**
     * 序列化时忽略null值
     * @param object 待序列化对象
     * @param <T> 待序列化对象类型
     * @return json
     */
    public static <T> Optional<String> serializeIgnoreNull(T object) {
        if (null == object) {
            return Optional.empty();
        }
        try {
            return Optional.of(IGNORE_NULL_MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            LOGGER.error("Serialize to json occurs exception", e);
        }
        return Optional.empty();
    }

    public static <T> Optional<T> deserialize(String jsonStr, Class<T> cls) {
        if (null == jsonStr || "".equals(jsonStr.trim())) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.readValue(jsonStr, cls));
        } catch (Exception e) {
            LOGGER.error("Deserialize string {} to Object occurs exception", jsonStr, e);
        }
        return Optional.empty();
    }

    public static <T> Optional<T> deserialize(String jsonStr, JavaType type) {
        if (null == jsonStr || "".equals(jsonStr.trim())) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.readValue(jsonStr, type));
        } catch (Exception e) {
            LOGGER.error("Deserialize string {} to Object occurs exception", jsonStr, e);
        }
        return Optional.empty();
    }

    public static <T> Optional<T> deserialize(InputStream is, Class<T> cls) {
        if (null == is) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.readValue(is, cls));
        } catch (Exception e) {
            LOGGER.error("Deserialize input to Object occurs exception", e);
        }
        return Optional.empty();
    }

    public static <T> Optional<T> deserialize(InputStream is, JavaType type) {
        if (null == is) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.readValue(is, type));
        } catch (Exception e) {
            LOGGER.error("Deserialize input to Object occurs exception", e);
        }
        return Optional.empty();
    }

    /**
     * 从文件流读取json字符串
     *
     * @param is 文件流
     * @return Optional包装的json字符串
     */
    public static Optional<String> readJsonFormFile(InputStream is) {
        if (null == is) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.readTree(is).toString());
        } catch (Exception e) {
            LOGGER.error("Read json from file occurs exception", e);
        }
        return Optional.empty();
    }

    /**
     * 从文件流读取json对象
     *
     * @param is 文件流
     * @return Optional包装的json对象
     */
    public static Optional<JsonNode> readJsonNodeFormFile(InputStream is) {
        if (null == is) {
            return Optional.empty();
        }
        try {
            return Optional.of(MAPPER.readTree(is));
        } catch (Exception e) {
            LOGGER.error("Read json from file occurs exception", e);
        }
        return Optional.empty();
    }

    /**
     * 封装对象转换为string map
     *
     * <p>es array字段单个更新
     *
     * @param object 待转换对象
     * @return {key: 属性, value: 值}
     */
    public static <T> Map<String, Object> toStringMap(T object) {
        return MAPPER.convertValue(object, OBJECT_TO_STRING_MAP_REF);
    }

    /**
     * 集合封装对象转换为list string map
     *
     * <p>es array字段批量更新使用
     *
     * @param object 待转换对象
     * @return 转换后的list sting map
     */
    public static List<Map<String, Object>> toListStringMap(Object object) {
        return MAPPER.convertValue(object, OBJECT_TO_LIST_STRING_MAP_REF);
    }

    /**
     * 支持jsr310 types的object mapper
     *
     * @return
     */
    public static ObjectMapper jsr310ObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule().addSerializer(
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        ).addDeserializer(
                LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        ));
        return objectMapper;
    }

    /**
     * 判断是否为json串
     *
     * @param json json字符串
     * @return 是否是json
     */
    public static boolean isJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            return deserialize(json, Map.class).isPresent();
        } catch (Exception e) {
            return false;
        }
    }
}