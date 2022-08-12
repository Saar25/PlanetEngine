package org.saar.core.screen;

import org.saar.core.screen.annotations.DepthScreenImageProperty;
import org.saar.core.screen.annotations.DepthStencilScreenImageProperty;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.annotations.StencilScreenImageProperty;
import org.saar.core.screen.image.ScreenImage;
import org.saar.core.util.reflection.FieldsLocator;

import java.util.List;
import java.util.function.Predicate;

public final class ScreenImagesLocator {

    private final FieldsLocator fieldsLocator;

    public ScreenImagesLocator(ScreenPrototype prototype) {
        this.fieldsLocator = new FieldsLocator(prototype);
    }

    public List<ScreenImagePrototype> getScreenImagesPrototypes() {
        return this.fieldsLocator.getFilteredValues(ScreenImagePrototype.class, ScreenImageProperty.class);
    }

    public List<ScreenImagePrototype> getDepthScreenImagesPrototypes() {
        return this.fieldsLocator.getFilteredValues(ScreenImagePrototype.class, DepthScreenImageProperty.class);
    }

    public List<ScreenImagePrototype> getStencilScreenImagesPrototypes() {
        return this.fieldsLocator.getFilteredValues(ScreenImagePrototype.class, StencilScreenImageProperty.class);
    }

    public List<ScreenImagePrototype> getDepthStencilScreenImagesPrototypes() {
        return this.fieldsLocator.getFilteredValues(ScreenImagePrototype.class, DepthStencilScreenImageProperty.class);
    }

    public List<ScreenImage> getScreenImages() {
        return this.fieldsLocator.getFilteredValues(ScreenImage.class, ScreenImageProperty.class);
    }

    public List<ScreenImage> getDrawScreenImage() {
        return filterAnnotated(ScreenImageProperty::draw);
    }

    public List<ScreenImage> getReadScreenImages() {
        return filterAnnotated(ScreenImageProperty::read);
    }

    private List<ScreenImage> filterAnnotated(Predicate<ScreenImageProperty> predicate) {
        return this.fieldsLocator.getFilteredValues(ScreenImage.class, ScreenImageProperty.class,
                field -> predicate.test(field.getAnnotation(ScreenImageProperty.class)));
    }

}
