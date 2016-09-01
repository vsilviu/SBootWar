package generic.module.two.service.util;

import generic.module.two.aspectj.decorator.JVMCacheable;
import generic.module.two.dto.GenericDTO;
import generic.module.two.entity.decorator.Identificable;
import generic.module.two.entity.decorator.Parentable;
import org.apache.commons.beanutils.PropertyUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Component
public class ClassUtil {


    private static final String _package = "com.corpo.komm";

    private static ThreadLocal<Exception> _exception = new ThreadLocal<Exception>();


    public Class getFirstSuperClass(Class c) {
        return getNSuperClass(c, 0);
    }

    public Class getSecondSuperClass(Class c) {
        return getNSuperClass(c, 1);
    }

    @JVMCacheable
    public boolean hasInterface(Class c, Class _interface) {
        boolean result = Arrays.stream(c.getInterfaces()).filter(t -> t.equals(_interface)).count() > 0;
        if (!result && c.getGenericSuperclass() != null) {
            return hasInterface((Class) c.getGenericSuperclass(), _interface);
        }

        return result;
    }

    private Class getNSuperClass(Class c, int n) {
        return (Class) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[n];
    }

    public void setParentableProperty(Object o, Object value) throws Exception {
        _exception.set(null);
        Arrays.stream(o.getClass().getDeclaredFields()).forEach(f -> {
            Arrays.stream(f.getDeclaredAnnotations()).filter(a -> a instanceof Parentable).forEach(a -> {
                try {
                    PropertyUtils.setProperty(o, f.getName(), value);
                } catch (Exception e) {
                    _exception.set(e);
                }
            });
        });

        if (_exception.get() != null) {
            throw _exception.get();
        }

    }

    @JVMCacheable
    public Class getClassForName(String name) {

        Reflections reflections = new Reflections(_package);
        Set<Class<? extends GenericDTO>> classes =
                reflections.getSubTypesOf(GenericDTO.class);

        Optional<Class<? extends GenericDTO>> classStream = classes
                .stream()
                .filter(c -> c.getName().endsWith(name))
                .findAny();

        if (classStream.isPresent()) return classStream.get();
        return null;
    }

    @JVMCacheable
    public Class getJpaEntityForTableName(String tableName) {

        Reflections reflections = new Reflections(_package);
        Set<Class<? extends Identificable>> classes =
                reflections.getSubTypesOf(Identificable.class);

        Optional<Class<? extends Identificable>> classStream = classes
                .stream()
                .filter(c -> c.isAnnotationPresent(Table.class) &&
                        (c.getAnnotation(Table.class).name().equals(tableName)))
                .findAny();

        if (classStream.isPresent()) return classStream.get();
        return null;
    }


}
