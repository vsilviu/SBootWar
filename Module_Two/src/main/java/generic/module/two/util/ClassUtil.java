package generic.module.two.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sache on 8/12/2016.
 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    private static ClassUtil instance;

    protected ClassUtil() {
    }

    public static synchronized ClassUtil getInstance() {
        if (instance == null) {
            instance = new ClassUtil();
        }
        return instance;
    }

    public static boolean isProjectSpecificClass(Class cl) {
        return cl != null && !(ClassUtils.isPrimitiveOrWrapper(cl) || cl.equals(String.class));

    }

    public static boolean isFieldList(Field field) {
        return field != null && field.getType() != null && field.getType().equals(List.class);
    }

    public static boolean isFieldCollection(Field field) {
        return field != null && field.getType() != null && field.getType().equals(Collection.class);
    }

    public static boolean isFieldArrayList(Field field) {
            return field != null && field.getType() != null && field.getType().equals(ArrayList.class);
    }

    public static boolean isFieldLinkedList(Field field) {
        return field != null && field.getType() != null && field.getType().equals(LinkedList.class);
    }

    public static Object runGetter(String fieldName, Object o, Object... params) {

        return genericAction("get", fieldName, o, params);

    }

    private static Object genericAction(String action, String fieldName, Object o, Object[] params) {
        if (StringUtils.isEmpty(fieldName))
            return null;

        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith(action)) && (method.getName().length() == (fieldName.length() + 3))
                    && method.getName().toLowerCase().endsWith(fieldName.toLowerCase())) {
                try {
                    return method.invoke(o, params);
                } catch (ReflectiveOperationException e) {
                    logger.error(ExceptionUtils.getMessage(e), e);
                }
            }
        }
        return null;
    }

    public static Object runGetterWithExactName(String getterName, Object o, Object... params) {

        if (StringUtils.isEmpty(getterName)) {
            return null;
        }

        for (Method method : o.getClass().getMethods()) {
            if (method.getName().compareTo(getterName) != 0) {
                continue;
            }
            try {
                return method.invoke(o, params);
            } catch (ReflectiveOperationException e) {
                logger.error(ExceptionUtils.getMessage(e), e);
            }

        }

        return null;
    }

    public static Object runSetter(String fieldName, Object o, Object... params) {
        return genericAction("set", fieldName, o, params);
    }
}
