package org.saar.core.model.vertex;

public class ModelAttributes {

    private final ModelAttribute[] attributes;

    public ModelAttributes(ModelAttribute... attributes) {
        this.attributes = attributes;
    }

    public ModelAttribute[] getAttributes() {
        return this.attributes;
    }

    public int count() {
        return getAttributes().length;
    }

}
