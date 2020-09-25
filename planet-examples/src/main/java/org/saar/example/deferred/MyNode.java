package org.saar.example.deferred;

import org.saar.core.common.obj.ObjNode;
import org.saar.core.node.AbstractNode;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.SimpleTransform;

public class MyNode extends AbstractNode implements ObjNode {

    private final SimpleTransform transform = new SimpleTransform();

    private final ReadOnlyTexture texture;

    public MyNode(ReadOnlyTexture texture) {
        this.texture = texture;
    }

    @Override
    public SimpleTransform getTransform() {
        return this.transform;
    }

    @Override
    public ReadOnlyTexture getTexture() {
        return this.texture;
    }
}
