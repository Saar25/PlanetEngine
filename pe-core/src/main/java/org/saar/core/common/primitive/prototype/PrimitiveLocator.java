package org.saar.core.common.primitive.prototype;

import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.utils.reflection.FieldsLocator;

import java.lang.reflect.Field;
import java.util.List;

public final class PrimitiveLocator {

    private final FieldsLocator fieldsLocator;

    public PrimitiveLocator(PrimitivePrototype prototype) {
        this.fieldsLocator = new FieldsLocator(prototype);
    }

    public List<GlPrimitive> getPrimitiveProperties() {
        final Class<PrimitiveProperty> annotation = PrimitiveProperty.class;
        final List<Field> fields = fieldsLocator.getAnnotatedFields(annotation);
        return fieldsLocator.getValues(GlPrimitive.class, fields);
    }

}
