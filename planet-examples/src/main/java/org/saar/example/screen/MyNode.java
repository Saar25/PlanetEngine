package org.saar.example.screen;

import org.saar.core.common.obj.ObjNode;
import org.saar.core.node.AbstractNode;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.objects.Transform;

public class MyNode extends AbstractNode implements ObjNode {

    private final Transform transform = new Transform();

    private final ReadOnlyTexture texture;

    public MyNode(ReadOnlyTexture texture) {
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
