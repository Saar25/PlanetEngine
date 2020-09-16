package org.saar.example;

import org.saar.utils.property.ChangeListener;
import org.saar.utils.property.type.FloatProperty;

public class Check {

    public static void main(String[] args) {
        final FloatProperty floatProperty = new FloatProperty(4f);
        floatProperty.set(8f);

        System.out.println(floatProperty.get());

        final ChangeListener<Float> listener = e -> System.out.println(
                e.getOldValue() + " -> " + e.getNewValue());

        floatProperty.addListener(listener);
        floatProperty.set(8f);
        floatProperty.set(9f);
        floatProperty.addListener(listener);
        floatProperty.set(10f);
        floatProperty.removeListener(listener);
        floatProperty.set(11f);
    }

}
