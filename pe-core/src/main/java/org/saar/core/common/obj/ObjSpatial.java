package org.saar.core.common.obj;

import org.saar.core.node.Spatial;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.Transform;

public class ObjSpatial extends Spatial implements ObjNode {

    private final ReadOnlyTexture texture;

    public ObjSpatial(ReadOnlyTexture texture) {
        this.texture = texture;
    }

    @Override
    public Transform getWorldTransform() {
        return getLocalTransform();
    }

    @Override
    public Transform getTransform() {
        return getLocalTransform();
    }

    @Override
    public ReadOnlyTexture getTexture() {
        return this.texture;
    }
}
