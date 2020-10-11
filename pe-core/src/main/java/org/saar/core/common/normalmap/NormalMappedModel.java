package org.saar.core.common.normalmap;

import org.saar.core.node.Model;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.Transform;

public class NormalMappedModel implements Model, NormalMappedNode {

    private final NormalMappedMesh mesh;
    private final NormalMappedNode instance;

    public NormalMappedModel(NormalMappedMesh mesh, NormalMappedNode instance) {
        this.mesh = mesh;
        this.instance = instance;
    }

    @Override
    public Transform getTransform() {
        return this.instance.getTransform();
    }

    @Override
    public ReadOnlyTexture getTexture() {
        return this.instance.getTexture();
    }

    @Override
    public ReadOnlyTexture getNormalMap() {
        return this.instance.getNormalMap();
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
