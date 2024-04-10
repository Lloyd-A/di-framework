package allen.lloyd.framework.di.impl;

import allen.lloyd.framework.di.exception.DIFException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.io.Resources.getResource;
import static java.util.stream.Collectors.toMap;

@Slf4j
public class ApplicationPropertiesLoader {
    public static Map<String, String> loadProperties() {
        log.debug("Loading properties from application.properties");
        try {
            final var resource = getResource("application.properties");
            log.debug("Resource to be loaded: {}", resource);
            try (var props = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                Map<String, String> properties = props.lines()
                        .map(String::trim)
                        .map(line -> line.split("="))
                        .collect(toMap(line -> line[0].trim(), line -> line[1].trim()));
                log.info("Loaded {} properties from application.properties", properties.size());
                return properties;
            } catch (IOException e) {
                log.error("Unable to load application.properties", e);
                throw new DIFException("Unable to load application.properties", e);
            }
        } catch (IllegalArgumentException e) {
            log.warn("Resource application.properties not found. Returning empty properties map.", e);
            return new HashMap<>(0);
        }
    }
}
