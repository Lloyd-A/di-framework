package allen.lloyd.framework.di.utils;

import allen.lloyd.framework.di.exception.DIFException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.reflections.Reflections.log;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class DIFObjectUtils {
    public static <T> T checkNonNullOrThrowException(T t) {
        return checkNonNullOrThrowException(t, "Object is null");
    }

    public static <T> T checkNonNullOrThrowException(T t, String msg) {
        if (t == null) {
            throw new DIFException(msg, new NullPointerException());
        }
        return t;
    }

    public static boolean isBlank(String string) {
        if (string == null || string.isEmpty() || string.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static <T> T castToType(Object obj, Class<T> clazz) {
        log.debug("Trying to cast an object of type {} to {}", obj.getClass(), clazz);
        try {
            return clazz.cast(obj);
        } catch (ClassCastException classCastException) {
            log.error("Failed to cast object of type {} to {}", obj.getClass(), clazz, classCastException);
            throw new DIFException("Cannot cast " + obj.getClass() + " to " + clazz, classCastException);
        }
    }

    public static Class<?> loadClassByName(String fullyQualifiedName){
        try {
            log.debug("Loading class: " + fullyQualifiedName);
            return Class.forName(fullyQualifiedName);
        } catch (ClassNotFoundException e) {
            log.error("Unable lo load class: " + fullyQualifiedName, e);
            throw new DIFException("Unable lo load class: " + fullyQualifiedName, e);
        }
    }


    public static <T> T instantiate(Class<T> clazz) {
        log.debug("Trying to instantiate {}", clazz);
        try {
            final var constructors = clazz.getDeclaredConstructors();
            if (constructors.length != 1 ) {
                log.error("Unable to instance " + clazz +" beans should have only public default constructors");
                throw new DIFException("Unable to instance " + clazz +" beans should have only public default constructors");
            }
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            log.error("The class {} is not instantiable (it’s abstract or an interface)", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        } catch (IllegalAccessException e) {
            log.error("The constructor of class {} is not accessible", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        } catch (InvocationTargetException e) {
            log.error("The constructor of class {} threw an exception", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        } catch (NoSuchMethodException e) {
            log.error("The class {} does not have a no-argument constructor", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        }
    }


    public static boolean trueIfSequenceNullOrEmpty(Iterable<?> iterable) {
        if (iterable != null && iterable.iterator().hasNext()) {
            return false;
        }
        return true;
    }

    public static Set<Field> getDeclaredFields(Class<?> object) {
        final Set<Field> fields = new HashSet<>();
        getDeclaredFields(object, fields);
        return fields;
    }

    private static void getDeclaredFields(final Class<?> clazz, final Set<Field> fields) {
        log.debug("Processing class: {}", clazz);

        Field[] declaredFields = clazz.getDeclaredFields();
        Collections.addAll(fields, declaredFields);

        log.debug("Found {} declared field(s) in class: {}", declaredFields.length, clazz);

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            log.info("Moving to superclass: {}", superclass);
            getDeclaredFields(superclass, fields);
        } else {
            log.info("No valid superclass for class: {}", clazz);
        }
    }


    public static void setField(Field field, Object intance, Object value) {
        try {
            log.debug("Setting field " + field + " of " + intance + " with " + value);
            field.setAccessible(true);
            field.set(intance, value);
        } catch (IllegalAccessException e) {
            log.error("Error during setting field " + field + " of " + intance + " with " + value);
            throw new DIFException("Unable to set field: " + field, e);
        }
    }
}
