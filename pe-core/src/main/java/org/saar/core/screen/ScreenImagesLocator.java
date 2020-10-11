package org.saar.core.screen;

import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.utils.reflection.FieldsLocator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

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

    public List<ColourScreenImage> getDrawScreenImage() {
        final List<Field> fields = filterAnnotated(ScreenImageProperty::draw);
        return fieldsLocator.getValues(ColourScreenImage.class, fields);
    }

    public List<ColourScreenImage> getReadScreenImages() {
        final List<Field> fields = filterAnnotated(ScreenImageProperty::read);
        return fieldsLocator.getValues(ColourScreenImage.class, fields);
    }

    private List<Field> filterAnnotated(Predicate<ScreenImageProperty> predicate) {
        final Class<ScreenImageProperty> annotation = ScreenImageProperty.class;
        final List<Field> fields = fieldsLocator.getAnnotatedFields(annotation);
        return fieldsLocator.filterByAnnotation(fields, annotation, predicate);
    }

}
