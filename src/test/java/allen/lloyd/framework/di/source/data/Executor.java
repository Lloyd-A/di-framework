package allen.lloyd.framework.di.source.data;

import allen.lloyd.framework.di.annotations.InjectType;
import allen.lloyd.framework.di.annotations.TypeToInject;

@TypeToInject(singleton = false)
public class Executor {
    @InjectType
    private Starter starter;
    @InjectType(type ="allen.lloyd.framework.di.source.data.impl.WriterImpl")
    private Writer writer;

    public void run(String str) {
        writer.log("Executor it running: " + str);
        starter.start();
    }
}
