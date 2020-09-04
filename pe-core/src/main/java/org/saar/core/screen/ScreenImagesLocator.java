package org.saar.core.screen;

import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ScreenImage;
import org.saar.utils.reflection.FieldsLocator;

import java.lang.reflect.Field;
import java.util.List;

public final class ScreenImagesLocator {

    private final FieldsLocator fieldsLocator;

    public ScreenImagesLocator(ScreenPrototype prototype) {
        this.fieldsLocator = new FieldsLocator(prototype);
    }

    public List<ScreenImage> getScreenImages() {
        final Class<ScreenImageProperty> annotation = ScreenImageProperty.class;
        final List<Field> fields = fieldsLocator.getAnnotatedFields(annotation);
        return fieldsLocator.getValues(ScreenImage.class, fields);
    }

}
