package org.saar.core.model.data;

public class ModelDataInfo {

    private final int componentCount;
    private final boolean normalized;

    public ModelDataInfo(int componentCount, boolean normalized) {
        this.componentCount = componentCount;
        this.normalized = normalized;
    }

    public int getComponentCount() {
        return componentCount;
    }

    public boolean isNormalized() {
        return normalized;
    }
}
