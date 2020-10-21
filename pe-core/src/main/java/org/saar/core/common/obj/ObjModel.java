package org.saar.core.common.obj;

import org.saar.core.node.Model;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.Transform;

public class ObjModel implements Model {

    private final ObjMesh mesh;
    private final ObjNode instance;

    public ObjModel(ObjMesh mesh, ObjNode instance) {
        this.mesh = mesh;
        this.instance = instance;
    }

    public Transform getTransform() {
        return this.instance.getTransform();
    }

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
