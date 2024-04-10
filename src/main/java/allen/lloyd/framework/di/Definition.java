package allen.lloyd.framework.di;

import allen.lloyd.framework.di.annotations.TypeToInject;
import allen.lloyd.framework.di.impl.DefinitionImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static allen.lloyd.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

public interface Definition extends Comparable<Definition> {
    static DefinitionImpl getDefinition(Class<?> aClass) {
        checkNonNullOrThrowException(aClass, "Instance can't be null");
        final var name = aClass.getCanonicalName();
        final var aliases = getAliases(aClass, new ArrayList<>());
        final var singleton = aClass.getAnnotation(TypeToInject.class).singleton();
        return new DefinitionImpl(name, singleton, aliases);
    }

    static List<String> getAliases(Class<?> item, List<String> aliases) {
        if (item.getSuperclass() == null) {
            return aliases;
        }
        Arrays.stream(item.getInterfaces()).map(Class::getCanonicalName).forEach(aliases::add);
        aliases.add(item.getCanonicalName());
        return getAliases(item.getSuperclass(), aliases);
    }

    String name();

    Iterable<String> aliases();

    boolean isSingleton();
}
