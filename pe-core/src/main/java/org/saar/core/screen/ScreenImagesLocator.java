package org.saar.core.screen;

import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ScreenImage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ScreenImagesLocator {

    private final ScreenPrototype prototype;

    public ScreenImagesLocator(ScreenPrototype prototype) {
        this.prototype = prototype;
    }

    public List<ScreenImage> getScreenImages() {
        final Class<ScreenImageProperty> annotation = ScreenImageProperty.class;
        final List<Field> fields = getAnnotatedFields(getAllFields(), annotation);
        return mapFields(ScreenImage.class, fields);
    }

    private <T> List<T> mapFields(Class<T> cast, List<Field> fields) {
        final List<T> objects = new ArrayList<>(fields.size());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                final Object object = field.get(this.prototype);
                objects.add(cast.cast(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    private List<Field> getAllFields() {
        final ArrayList<Field> fields = new ArrayList<>();
        final Class<?> iClass = this.prototype.getClass();
        Collections.addAll(fields, iClass.getDeclaredFields());
        return fields;
    }

    private List<Field> getAnnotatedFields(List<Field> fields, Class<? extends Annotation> annotation) {
        final List<Field> annotatedFields = new ArrayList<>(fields);
        annotatedFields.removeIf(f -> !f.isAnnotationPresent(annotation));
        return annotatedFields;
    }

}
