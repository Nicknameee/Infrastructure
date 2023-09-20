package io.management.ua.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.management.ua.annotations.Value;
import io.management.ua.exceptions.BeanProcessingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValueAnnotationProcessor implements BeanPostProcessor {
    private final Environment environment;
    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Value.class)) {
                Value annotation = field.getAnnotation(Value.class);

                String location = annotation.resource();

                Resource resource = new ClassPathResource(location);

                Properties properties = new Properties();
                Object value = null;
                try {
                    if (location.endsWith(".yml")) {
                        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
                        JsonNode node = objectMapper.readTree(resource.getInputStream());
                        String[] path = annotation.property().split("\\.");
                        for (String element : path) {
                            if (node != null) {
                                node = node.get(element);
                            }
                        }
                        if (node != null) {
                            value = node.asText();
                        }
                    } else {
                        properties.load(resource.getInputStream());
                        value = properties.getProperty(annotation.property());
                    }
                } catch (IOException e) {
                    log.error("Could not load property");
                }

                ReflectionUtils.makeAccessible(field);

                if (value == null) {
                    if (annotation.required()) {
                        throw new BeanProcessingException("Required property could not be injected, no value found, check the resource name or property path");
                    }
                } else {
                    if (value.toString().startsWith("${")) {
                        String envPropertyName = value.toString().substring(2, Math.min(value.toString().indexOf(":"), value.toString().indexOf("}")));
                        value = environment.getProperty(envPropertyName);
                    }
                    injectProperty(field, bean, value);
                }
            }
        }

        return bean;
    }

    private void injectProperty(Field field, Object bean, Object value) {
        if (field.getType().equals(Long.class)) {
            ReflectionUtils.setField(field, bean, Long.parseLong(value.toString()));
        } else if (field.getType().equals(Integer.class)) {
            ReflectionUtils.setField(field, bean, Integer.parseInt(value.toString()));
        } else if (field.getType().equals(Float.class)) {
            ReflectionUtils.setField(field, bean, Float.parseFloat(value.toString()));
        } else if (field.getType().equals(String.class)) {
            ReflectionUtils.setField(field, bean, value.toString());
        } else if (field.getType().equals(Double.class)) {
            ReflectionUtils.setField(field, bean, Double.parseDouble(value.toString()));
        } else if (field.getType().equals(Boolean.class)) {
            ReflectionUtils.setField(field, bean, Boolean.parseBoolean(value.toString()));
        } else if (field.getType().equals(Character.class)) {
            ReflectionUtils.setField(field, bean, value);
        } else if (field.getType().equals(Short.class)) {
            ReflectionUtils.setField(field, bean, Short.parseShort(value.toString()));
        }
        field.setAccessible(false);
    }
}
