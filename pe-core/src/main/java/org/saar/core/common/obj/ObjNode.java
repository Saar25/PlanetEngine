package org.saar.core.common.obj;

import org.saar.core.node.AbstractNode;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.objects.Transform;

public class ObjNode extends AbstractNode implements IObjNode {

    private final Transform transform;
    private final ReadOnlyTexture texture;

    public ObjNode(ReadOnlyTexture texture) {
        this(new Transform(), texture);
    }

    public ObjNode(Transform transform, ReadOnlyTexture texture) {
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
