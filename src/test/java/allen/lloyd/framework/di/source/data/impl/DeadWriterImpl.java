package allen.lloyd.framework.di.source.data.impl;

import allen.lloyd.framework.di.annotations.TypeToInject;
import allen.lloyd.framework.di.source.data.Writer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TypeToInject
public class DeadWriterImpl implements Writer {
    @Override
    public void log(String msg) {
        log.error("You should not see that");
    }
}
