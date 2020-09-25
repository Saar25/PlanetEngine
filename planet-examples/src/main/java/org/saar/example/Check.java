package org.saar.example;

import org.jproperty.type.*;

public class Check {

    public static void main(String[] args) {
        final FloatProperty property1 = new SimpleFloatProperty(4f);
        final IntProperty property2 = new SimpleIntProperty(8);
        final ReadOnlyFloatProperty f = new ConstantFloatProperty(5);

        property1.bindBidirectional(property2);

        System.out.println(property1.get());
        property2.set(7);
        System.out.println(property1.get());
        property1.set(3f);
        System.out.println(property1.get());
        System.out.println(property2.get());
    }

}
