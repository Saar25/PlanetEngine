package org.saar.core.common.flatreflected;

import org.saar.core.node.RenderNode;
import org.saar.maths.transform.SimpleTransform;

public class FlatReflectedRenderNode implements RenderNode {

    private final FlatReflectedMesh mesh;

    private final SimpleTransform transform = new SimpleTransform();

    public FlatReflectedRenderNode(FlatReflectedMesh mesh) {
        this.mesh = mesh;
    }

    public SimpleTransform getTransform() {
        return this.transform;
    }

    @Override
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}
