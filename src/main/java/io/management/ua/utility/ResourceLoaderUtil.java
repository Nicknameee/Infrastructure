package io.management.ua.utility;

import io.management.ua.annotations.Export;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;

public class ResourceLoaderUtil {
    @Export
    public static String getResourceContent(String location) {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        return getResourceContent(resource);
    }

    private static String getResourceContent(Resource resource) {
        try {
            String content = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
            return content.replace("\r\n", "\n");
        } catch (IOException e) {
            return null;
        }
    }
}