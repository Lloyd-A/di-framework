package allen.lloyd.framework.di;

import java.util.List;

public interface Context {
    Object getBean(String clazz);

    List<BeanProcessor> getBeanProcessors();
}
