package allen.lloyd.framework.di.impl;

import allen.lloyd.framework.di.BeanProcessor;
import allen.lloyd.framework.di.Configuration;
import allen.lloyd.framework.di.Definition;
import allen.lloyd.framework.di.annotations.TypeToInject;
import allen.lloyd.framework.di.exception.DIFException;
import allen.lloyd.framework.di.utils.DIFObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import java.util.Collection;

import java.util.ArrayList;
import java.util.List;

import static allen.lloyd.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;
import static lombok.AccessLevel.PUBLIC;
import static org.reflections.Reflections.log;

@Slf4j
@RequiredArgsConstructor(access = PUBLIC)
public class ConfigurationImpl implements Configuration {
    private final ArrayList<Definition> definitions = new ArrayList<>();
    private final ArrayList<BeanProcessor> beanProcessors = new ArrayList<>();
    private final List<Reflections> reflections;

    public void initDefinitions() {
        final var definitionList = reflections.stream()
                .map(ref -> ref.getTypesAnnotatedWith(TypeToInject.class))
                .flatMap(Collection::stream)
                .map(Definition::getDefinition)
                .toList();
        definitions.addAll(definitionList);

        var subTypesOfBeanProcessor = reflections.stream()
                .map(ref -> ref.getSubTypesOf(BeanProcessor.class))
                .flatMap(Collection::stream)
                .map(DIFObjectUtils::instantiate)
                .toList();
        beanProcessors.addAll(subTypesOfBeanProcessor);
    }

    @Override
    public Definition getDefinition(final String type) {
        checkNonNullOrThrowException(type, "Type can not be null");
        log.debug("Locking for the implementation of the type " + type);
        final var typeDefinitions = definitions.stream()
                .filter(object -> {
                    if (type.equals(object.name())) {
                        return true;
                    }
                    for (String alias : object.aliases()) {
                        if (type.equals(alias)) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();

        if (typeDefinitions.size() != 1) {
            log.error(type + " has " + typeDefinitions.size() + " implementations");
            throw new DIFException(type + " has 0 or more than 1 implementations", new IllegalArgumentException(type));
        }
        return typeDefinitions.get(0);
    }

    @Override
    public List<BeanProcessor> getBeanProcessors() {
        return beanProcessors;
    }
}
