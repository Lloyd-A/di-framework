package allen.lloyd.framework.di.source.data.impl;

import allen.lloyd.framework.di.annotations.TypeToInject;
import allen.lloyd.framework.di.source.data.Writer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TypeToInject
public class WriterImpl implements Writer {
    @Override
    public void log(String msg) {
        log.info("Logged: " + msg);
    }
}
