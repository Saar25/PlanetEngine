package org.saar.maths.transform;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.maths.utils.Matrix4;

public class ChildTransform implements Transform {

    private final Matrix4f transformation = Matrix4.create();

    private final Transform local;
    private final Transform parent;

    private final SimpleTransform helper = new SimpleTransform();

    public ChildTransform(Transform local, Transform parent) {
        this.local = local;
        this.parent = parent;
        initListeners();
    }

    private void initListeners() {
        this.local.getPosition().addListener(e -> updatePosition());
        this.local.getRotation().addListener(e -> updateRotation());
        this.local.getScale().addListener(e -> updateScale());

        this.parent.getPosition().addListener(e -> updatePosition());
        this.parent.getRotation().addListener(e -> updateRotation());
        this.parent.getScale().addListener(e -> updateScale());
    }

    private void updatePosition() {
        this.helper.getPosition().set(this.parent.getPosition());
        this.helper.getPosition().add(this.local.getPosition().getValue());
    }

    private void updateRotation() {
        this.helper.getRotation().set(this.parent.getRotation());
        this.helper.getRotation().rotate(this.local.getRotation().getValue());
    }

    private void updateScale() {
        this.helper.getScale().set(this.parent.getScale());
        this.helper.getScale().scale(this.local.getScale().getValue());
    }

    @Override
    public Matrix4fc getTransformationMatrix() {
        final Matrix4fc parent = this.parent.getTransformationMatrix();
        final Matrix4fc local = this.local.getTransformationMatrix();
        return parent.mul(local, this.transformation);
    }

    @Override
    public Position getPosition() {
        return this.helper.getPosition();
    }

    @Override
    public Rotation getRotation() {
        return this.helper.getRotation();
    }

    @Override
    public Scale getScale() {
        return this.helper.getScale();
    }
}
