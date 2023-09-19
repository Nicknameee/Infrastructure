package io.management.ua.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.management.ua.annotations.Value;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Properties;

@Component
@Slf4j
public class ValueAnnotationProcessor implements BeanPostProcessor {
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
                            node = node.get(element);
                        }
                        value = node.asText();
                    } else {
                        properties.load(resource.getInputStream());
                        value = properties.getProperty(annotation.property());
                    }
                } catch (IOException e) {
                    log.error("Could not load property");
                }

                ReflectionUtils.makeAccessible(field);

                if (annotation.required()) {
                    ReflectionUtils.setField(field, bean, Objects.requireNonNull(value));
                } else {
                    ReflectionUtils.setField(field, bean, value);
                }

            }
        }

        return bean;
    }
}
