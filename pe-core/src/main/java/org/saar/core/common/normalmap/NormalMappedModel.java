package org.saar.core.common.normalmap;

import org.saar.core.node.Model;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.transform.Transform;

public class NormalMappedModel implements Model {

    private final NormalMappedMesh mesh;
    private final ReadOnlyTexture texture;
    private final ReadOnlyTexture normalMap;

    private final Transform transform = new SimpleTransform();

    public NormalMappedModel(NormalMappedMesh mesh, ReadOnlyTexture texture, ReadOnlyTexture normalMap) {
        this.mesh = mesh;
        this.texture = texture;
        this.normalMap = normalMap;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public ReadOnlyTexture getTexture() {
        return this.texture;
    }

    public ReadOnlyTexture getNormalMap() {
        return this.normalMap;
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
