package org.saar.core.util.reflection;

import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldsLocator {

    private final Object object;

    public FieldsLocator(Object object) {
        this.object = object;
    }

    private Object fieldValue(Field field) {
        try {
            field.setAccessible(true);
            return field.get(this.object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> getFilteredValues(Class<T> tClass, Class<? extends Annotation> annotation, Predicate<Field> predicate) {
        final Set<Field> fields = annotatedFields(annotation);
        return toValues(tClass, fields.stream().filter(predicate));
    }

    public <T> List<T> getFilteredValues(Class<T> tClass, Class<? extends Annotation> annotation) {
        final Set<Field> fields = annotatedFields(annotation);
        return toValues(tClass, fields.stream());
    }

    public <T> List<T> getFilteredValues(Class<T> tClass) {
        final Field[] fields = tClass.getDeclaredFields();
        return toValues(tClass, Arrays.stream(fields));
    }

    @SuppressWarnings("unchecked")
    private Set<Field> annotatedFields(Class<? extends Annotation> annotation) {
        final Predicate<Field> fieldPredicate = ReflectionUtils.withAnnotation(annotation);
        return ReflectionUtils.getFields(this.object.getClass(), fieldPredicate);
    }

    private <T> List<T> toValues(Class<T> tClass, Stream<Field> stream) {
        return stream.map(this::fieldValue).filter(tClass::isInstance)
                .map(tClass::cast).collect(Collectors.toList());
    }
}
