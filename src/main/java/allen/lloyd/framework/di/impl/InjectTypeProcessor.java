package allen.lloyd.framework.di.impl;

import allen.lloyd.framework.di.BeanProcessor;
import allen.lloyd.framework.di.Context;
import allen.lloyd.framework.di.annotations.InjectType;
import allen.lloyd.framework.di.utils.DIFObjectUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

import static allen.lloyd.framework.di.utils.DIFObjectUtils.setField;

@Slf4j
public class InjectTypeProcessor implements BeanProcessor {
    @Override
    public void process(Context context, Object bean) {
        final Class<?> beanClass = bean.getClass();
        final var declaredFields = DIFObjectUtils.getDeclaredFields(beanClass);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectType.class)) {
                log.info("Found field to inject: " + field);
                final var annotation = field.getAnnotation(InjectType.class);
                String implClass = field.getType().getCanonicalName();
                if (!annotation.type().trim().isEmpty()) {
                    implClass = annotation.type();
                }
                final Object dependency = context.getBean(implClass);
                setField(field, bean, dependency);
            }
        }
    }
}
