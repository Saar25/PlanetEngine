package org.saar.core.common.normalmap;

import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.Transform;

public class NormalMappedRenderNode implements RenderNode, NormalMappedNode {

    private final NormalMappedMesh mesh;
    private final NormalMappedNode instance;

    public NormalMappedRenderNode(NormalMappedMesh mesh, NormalMappedNode instance) {
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
