package allen.lloyd.framework.di;

public interface ObjectFactory {
    <T> T create(Class<T> clazz);
}
