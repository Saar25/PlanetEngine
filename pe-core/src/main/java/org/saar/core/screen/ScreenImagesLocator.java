package org.saar.core.screen;

import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.util.reflection.FieldsLocator;

import java.util.List;

public final class ScreenImagesLocator {

    private final FieldsLocator fieldsLocator;

    public ScreenImagesLocator(ScreenPrototype prototype) {
        this.fieldsLocator = new FieldsLocator(prototype);
    }

    public List<ScreenImagePrototype> getScreenImages() {
        return this.fieldsLocator.getFilteredValues(ScreenImagePrototype.class, ScreenImageProperty.class);
    }

}
