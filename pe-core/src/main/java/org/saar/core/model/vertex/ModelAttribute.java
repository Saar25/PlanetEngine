package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.constants.DataType;

public class ModelAttribute {

    private final int componentCount;
    private final boolean normalized;
    private final DataType dataType;

    public ModelAttribute(int componentCount, boolean normalized, DataType dataType) {
        this.componentCount = componentCount;
        this.normalized = normalized;
        this.dataType = dataType;
    }

    public int getComponentCount() {
        return componentCount;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public DataType getDataType() {
        return dataType;
    }
}