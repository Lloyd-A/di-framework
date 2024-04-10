package allen.lloyd.framework.di.source.data.impl;

import allen.lloyd.framework.di.annotations.InjectProperty;
import allen.lloyd.framework.di.annotations.InjectType;
import allen.lloyd.framework.di.annotations.TypeToInject;
import allen.lloyd.framework.di.source.data.Starter;
import allen.lloyd.framework.di.source.data.Writer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TypeToInject
public class StarterImpl implements Starter {
    @InjectProperty(name = "prop")
    private String prop;
    @InjectProperty
    private String property;
    @InjectType(type = "allen.lloyd.framework.di.source.data.impl.WriterImpl")
    private Writer writer;
    @InjectType
    private DeadWriterImpl deadWriter;

    @Override
    public void start() {
        writer.log(property);
        deadWriter.log(prop);
        writer.log(prop);
    }
}
