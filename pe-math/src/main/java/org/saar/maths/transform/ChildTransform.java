package org.saar.maths.transform;

import org.joml.Matrix4fc;
import org.saar.maths.wrapper.Matrix4fWrapper;

public class ChildTransform implements Transform {

    private final Matrix4fWrapper wrapper = new Matrix4fWrapper();

    private final Transform local;
    private final Transform parent;

    private final Transform helper = new SimpleTransform();

    public ChildTransform(Transform local, Transform parent) {
        this.local = local;
        this.parent = parent;
    }

    @Override
    public Matrix4fc getTransformationMatrix() {
        final Matrix4fc parent = this.parent.getTransformationMatrix();
        final Matrix4fc local = this.local.getTransformationMatrix();
        return parent.mul(local, this.wrapper.getValue());
    }

    @Override
    public Position getPosition() {
        this.helper.getPosition().set(this.parent.getPosition());
        this.helper.getPosition().add(this.local.getPosition().getValue());
        return this.helper.getPosition();
    }

    @Override
    public Rotation getRotation() {
        this.helper.getRotation().set(this.parent.getRotation());
        this.helper.getRotation().rotate(this.local.getRotation().getValue());
        return this.helper.getRotation();
    }

    @Override
    public Scale getScale() {
        this.helper.getScale().set(this.parent.getScale());
        this.helper.getScale().scale(this.local.getScale().getValue());
        return this.helper.getScale();
    }
}
