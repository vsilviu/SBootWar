package generic.module.two.transformer;

import com.google.common.reflect.TypeToken;
import generic.module.two.util.ClassUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Generic transformer from one entity to another (best for entities to DTOs)
 * Created by Sache on 8/11/2016.
 */

/**
 * Generic transformer from Entity to three default levels of DTOs or any other custom DTO that
 * extends <D> (smalled DTO type). Also works in reverse, DTO to Entity.
 * This class can transform between any two types of entities as long as properties match or transformers are provided.
 *
 * @param <E> Entity type
 * @param <D> Smalled entity DTO type
 */
public abstract class GenericTransformer<E, D> {

    @FunctionalInterface
    public interface TransformerFunction<T, R> {
        R apply(T t) throws IllegalAccessException, InstantiationException;
    }

    private final TypeToken<E> entityTypeToken = new TypeToken<E>(getClass()) {
    };
    private final TypeToken<D> DTOTypeToken = new TypeToken<D>(getClass()) {
    };

    private Map<String, TransformerFunction<E, Object>> transformerFunctionMap = new HashMap<>();
    private Map<String, TransformerFunction<E, Object>> reverseTransformerFunctionMap = new HashMap<>();

    public GenericTransformer() {
        addDefaultTransformerFunctions();
    }

    @SuppressWarnings("unchecked")
    public D getDetails(E object) {
        D newObject = null;
        try {
            newObject = (D) DTOTypeToken.getRawType().newInstance();
            copyProperties(object, newObject, false);

        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return newObject;
    }

    public D getDetails(E object, Class<? extends D> customClass) {
        if (customClass == null) {
            return null;
        }

        D newObject;
        try {
            newObject = customClass.newInstance();
            copyProperties(object, newObject, false);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return newObject;
    }

    @SuppressWarnings("unchecked")
    public E getReverseDetails(Object object)
            throws IllegalAccessException, InstantiationException {
        E newObject = (E) entityTypeToken.getRawType().newInstance();
//        copyProperties(object, newObject, true);

        return newObject;
    }

    /**
     * Method to copy properties from source to target based on type and name of fields and transformers defined.
     *
     * @param source source entity
     * @param target target entity
     */
    private void copyProperties(E source, Object target, boolean reverse)
            throws InstantiationException, IllegalAccessException {

        if (target != null && source != null) {
            /*
             * Go through all the properties and select those which are project specific.
             * Also select all Collections that contain project specific types.
             */
            Set<String> propertiesSpecificToProject = new HashSet<>();
            getAllFieldsOfClass(source).stream().filter(field -> field.getType() != null).forEach(field -> {
                if (ClassUtil.isFieldList(field)) {
                    // Is list of project specific type
                    ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                    Class<?> classFromList = (Class<?>) stringListType.getActualTypeArguments()[0];
                    if (ClassUtil.isProjectSpecificClass(classFromList)) {
                        propertiesSpecificToProject.add(field.getName());
                    }
                } else if (ClassUtil.isProjectSpecificClass(field.getType())) {
                    // Is project specific type
                    propertiesSpecificToProject.add(field.getName());
                }
            });

            /*
             * Auto copy all types which are not project specific and are not in the ignore properties list.
             */
            BeanUtils.copyProperties(source, target, propertiesSpecificToProject
                    .toArray(new String[propertiesSpecificToProject.size()]));


            /*
             * Remove from properties specific for project the properties that are set to be ignored.
             * Go through each property and try to convert them.
             */
            for (String specialProperty : propertiesSpecificToProject) {
                Field targetField = ReflectionUtils.findField(target.getClass(), specialProperty);

                if (targetField != null) {
                    // Check which mapper to use. If transforming from DTO to Entity use reverseTransformer.
                    Map<String, TransformerFunction<E, Object>> transformerFunctionMap =
                            (reverse) ? this.reverseTransformerFunctionMap : this.transformerFunctionMap;

                    if (transformerFunctionMap.get(specialProperty) != null) {
                        GenericTransformer.TransformerFunction<E, Object> transformerFunction =
                                transformerFunctionMap.get(specialProperty);

                        ClassUtil.runSetter(specialProperty, target, transformerFunction.apply(source));
                    }
                }
            }
        }
    }


    private List<Field> getAllFieldsOfClass(Object obj) {
        List<Field> returnList = new ArrayList<>();
        boolean notStop = true;
        Class c = obj.getClass();

        while (notStop) {
            returnList.addAll(Arrays.asList(c.getDeclaredFields()));
            if (c.getSuperclass() != null && !c.getSuperclass().equals(Object.class)) {
                c = c.getSuperclass();
            } else {
                notStop = false;
            }
        }

        return returnList;
    }

    public GenericTransformer<E, D> addTransformerFunction(String property, TransformerFunction<E, Object> function) {
        transformerFunctionMap.put(property, function);
        return this;
    }

    public GenericTransformer<E, D> addReverseTransformerFunction(String property, TransformerFunction<E, Object> function) {
        reverseTransformerFunctionMap.put(property, function);
        return this;
    }

    /**
     * Place where transformer can get some default transformer functions for non primitive properties
     */
    public abstract void addDefaultTransformerFunctions();
}
