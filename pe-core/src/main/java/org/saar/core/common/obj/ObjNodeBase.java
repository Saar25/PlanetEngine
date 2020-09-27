package org.saar.core.common.obj;

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.SimpleTransform;

public class ObjNodeBase implements ObjNode {

    private final SimpleTransform transform;
    private final ReadOnlyTexture texture;

    public ObjNodeBase(ReadOnlyTexture texture) {
        this(new SimpleTransform(), texture);
    }

    public ObjNodeBase(SimpleTransform transform, ReadOnlyTexture texture) {
        this.transform = transform;
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
