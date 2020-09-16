package org.saar.example;

import org.saar.utils.property.type.FloatProperty;

public class Check {

    public static void main(String[] args) {
        final FloatProperty property1 = new FloatProperty(4f);
        final FloatProperty property2 = new FloatProperty(8f);

        property1.bindBidirectional(property2);

        System.out.println(property1.get());
        property2.set(7f);
        System.out.println(property1.get());
        property1.set(3f);
        System.out.println(property1.get());
        System.out.println(property2.get());
    }

}
