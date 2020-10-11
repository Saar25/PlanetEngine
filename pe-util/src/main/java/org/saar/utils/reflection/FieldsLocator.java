package org.saar.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FieldsLocator {

    private final Object object;

    public FieldsLocator(Object object) {
        this.object = object;
    }

    public <A extends Annotation> List<Field> filterByAnnotation(
            List<Field> fields, Class<A> annotation, Predicate<A> predicate) {
        final List<Field> filtered = new ArrayList<>(fields);
        filtered.removeIf(field -> !field.isAnnotationPresent(annotation) ||
                !predicate.test(field.getAnnotation(annotation)));
        return filtered;
    }

    public <T> List<T> getValues(Class<T> cast, List<Field> fields) {
        final List<T> objects = new ArrayList<>(fields.size());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                final Object object = field.get(this.object);
                objects.add(cast.cast(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public List<Object> getValues(List<Field> fields) {
        final List<Object> objects = new ArrayList<>(fields.size());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                objects.add(field.get(this.object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public List<Field> getAnnotatedFields(List<Field> fields, Class<? extends Annotation> annotation) {
        final List<Field> annotatedFields = new ArrayList<>(fields);
        annotatedFields.removeIf(f -> !f.isAnnotationPresent(annotation));
        return annotatedFields;
    }

    public List<Field> getAnnotatedFields(Class<? extends Annotation> annotation) {
        return getAnnotatedFields(getAllFields(), annotation);
    }

    public List<Field> getAllFields() {
        final ArrayList<Field> fields = new ArrayList<>();
        final Class<?> type = this.object.getClass();
        final Field[] array = type.getDeclaredFields();
        Collections.addAll(fields, array);
        return fields;
    }
}
