package org.saar.core.model.vertex;

import java.util.Arrays;
import java.util.List;

public class ModelAttributes {

    private final List<ModelAttribute> attributes;

    public ModelAttributes(ModelAttribute... attributes) {
        this.attributes = Arrays.asList(attributes);
    }

    public List<ModelAttribute> getAttributes() {
        return this.attributes;
    }

}
