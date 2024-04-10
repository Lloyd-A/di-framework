package allen.lloyd.framework.di.impl;

import allen.lloyd.framework.di.Definition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static lombok.AccessLevel.PUBLIC;

@Builder
@RequiredArgsConstructor(access = PUBLIC)
@Slf4j
public class DefinitionImpl implements Definition {
    private final String name;
    private final boolean singleton;
    private final List<String> aliases;

    @Override
    public String name() {
        return name;
    }

    @Override
    public Iterable<String> aliases() {
        return aliases;
    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public int compareTo(Definition o) {
        return name.compareTo(o.name());
    }
}
