package org.saar.core.common.obj;

import org.saar.core.node.AbstractNode;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.Transform;

public class ObjNodeBase extends AbstractNode implements ObjNode {

    private final Transform transform;
    private final ReadOnlyTexture texture;

    public ObjNodeBase(ReadOnlyTexture texture) {
        this(new Transform(), texture);
    }

    public ObjNodeBase(Transform transform, ReadOnlyTexture texture) {
        this.transform = transform;
        this.texture = texture;
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public ReadOnlyTexture getTexture() {
        return this.texture;
    }
}
