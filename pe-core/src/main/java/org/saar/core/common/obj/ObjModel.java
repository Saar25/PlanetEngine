package org.saar.core.common.obj;

import org.saar.core.mesh.Model;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.transform.Transform;

public class ObjModel implements Model {

    private final ObjMesh mesh;
    private final ReadOnlyTexture texture;

    private final Transform transform = new SimpleTransform();

    public ObjModel(ObjMesh mesh, ReadOnlyTexture texture) {
        this.mesh = mesh;
        this.texture = texture;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public ReadOnlyTexture getTexture() {
        return this.texture;
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
