package allen.lloyd.framework.di;

import allen.lloyd.framework.di.impl.ContextImpl;
import allen.lloyd.framework.di.impl.ObjectFactoryImpl;

import java.util.List;

public class DiFramework {
    public static Context frameYourWork() {
        final var config = Configuration.getInstance(List.of("allen.lloyd.framework.di"));
        final var context = ContextImpl.getInstance(config);
        final var objectFactory = ObjectFactoryImpl.getObjectFactory(context);
        context.setObjectFactory(objectFactory);
        return context;
    }
}
