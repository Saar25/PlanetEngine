package org.saar.example.obj;

import org.saar.core.node.AbstractNode;
import org.saar.core.renderer.obj.ObjNode;
import org.saar.lwjgl.opengl.textures.ITexture;
import org.saar.maths.objects.Transform;

public class MyNode extends AbstractNode implements ObjNode {

    private final Transform transform = new Transform();

    private final ITexture texture;

    public MyNode(ITexture texture) {
        this.texture = texture;
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public ITexture getTexture() {
        return this.texture;
    }
}
