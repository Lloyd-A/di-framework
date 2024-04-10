package allen.lloyd.framework.di.source;

import allen.lloyd.framework.di.DiFramework;
import allen.lloyd.framework.di.source.data.Executor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


import static allen.lloyd.framework.di.utils.DIFObjectUtils.castToType;

@Slf4j
public class FrameworkTest {
    @Test
    void testEntryPoint() {
        final var context = DiFramework.frameYourWork();
        var executor = castToType(context.getBean("allen.lloyd.framework.di.source.data.Executor"), Executor.class);
        executor.run("first");
        executor = castToType(context.getBean("allen.lloyd.framework.di.source.data.Executor"), Executor.class);
        executor.run("second");
    }
}
