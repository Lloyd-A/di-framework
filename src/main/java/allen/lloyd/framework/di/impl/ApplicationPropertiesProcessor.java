package allen.lloyd.framework.di.impl;

import allen.lloyd.framework.di.BeanProcessor;
import allen.lloyd.framework.di.Context;
import allen.lloyd.framework.di.annotations.InjectProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

import static allen.lloyd.framework.di.impl.ApplicationPropertiesLoader.loadProperties;
import static allen.lloyd.framework.di.utils.DIFObjectUtils.getDeclaredFields;
import static allen.lloyd.framework.di.utils.DIFObjectUtils.setField;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.reflections.Reflections.log;


@Slf4j
public class ApplicationPropertiesProcessor implements BeanProcessor {
    private final Map<String, String> props;

    public ApplicationPropertiesProcessor() {
        this.props = loadProperties();
    }
    @Override
    @SneakyThrows
    public void process(Context context, Object bean) {
        final Class<?> newInstanceClass = bean.getClass();
        log.info("Processing bean of class: {}", newInstanceClass.getName());
        final var declaredFields = getDeclaredFields(newInstanceClass);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                final var annotation = field.getAnnotation(InjectProperty.class);
                String propertyName;
                if (isBlank(annotation.name())) {
                    propertyName = field.getName();
                } else {
                    propertyName = annotation.name();
                }
                final var properties = loadProperties();
                final var value = properties.get(propertyName);

                log.debug("Injecting property: {} with value: {} to field: {} of bean: {}", propertyName, value, field.getName(), newInstanceClass.getName());
                try {
                    setField(field, bean, value);
                    log.debug("Property: {} injected successfully", propertyName);
                } catch (Exception e) {
                    log.error("Error injecting property: {} to field: {} of bean: {}", propertyName, field.getName(), newInstanceClass.getName(), e);
                }
            }
        }
        log.info("Processing completed for bean of class: {}", newInstanceClass.getName());
    }
}
