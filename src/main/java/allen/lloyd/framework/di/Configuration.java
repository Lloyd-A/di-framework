package allen.lloyd.framework.di;

import java.util.List;

import allen.lloyd.framework.di.impl.ConfigurationImpl;
import org.reflections.Reflections;

import static allen.lloyd.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

public interface Configuration {
    static Configuration getInstance(List<String> packagesToScan) {
        checkNonNullOrThrowException(packagesToScan, "Packages list is null or empty");
        final var list = packagesToScan.stream().map(Reflections::new).toList();
        final var config = new ConfigurationImpl(list);
        config.initDefinitions();
        return config;
    }

    Definition getDefinition(String type);

    List<BeanProcessor> getBeanProcessors();
}
