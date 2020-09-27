package org.saar.core.common.obj;

import org.saar.core.node.RenderNode;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.Transform;

public class ObjRenderNode implements RenderNode, ObjNode {

    private final ObjMesh mesh;
    private final ObjNode instance;

    public ObjRenderNode(ObjMesh mesh, ObjNode instance) {
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
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}
