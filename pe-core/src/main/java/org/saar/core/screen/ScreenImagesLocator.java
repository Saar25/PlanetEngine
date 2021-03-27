package org.saar.core.screen;

import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.core.util.reflection.FieldsLocator;

import java.util.List;
import java.util.function.Predicate;

public final class ScreenImagesLocator {

    private final FieldsLocator fieldsLocator;

    public ScreenImagesLocator(ScreenPrototype prototype) {
        this.fieldsLocator = new FieldsLocator(prototype);
    }

    public List<ScreenImage> getScreenImages() {
        return this.fieldsLocator.getFilteredValues(ScreenImage.class, ScreenImageProperty.class);
    }

    public List<ColourScreenImage> getDrawScreenImage() {
        return filterAnnotated(ScreenImageProperty::draw);
    }

    public List<ColourScreenImage> getReadScreenImages() {
        return filterAnnotated(ScreenImageProperty::read);
    }

    private List<ColourScreenImage> filterAnnotated(Predicate<ScreenImageProperty> predicate) {
        return this.fieldsLocator.getFilteredValues(ColourScreenImage.class, ScreenImageProperty.class,
                field -> predicate.test(field.getAnnotation(ScreenImageProperty.class)));
    }

}
