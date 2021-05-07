package org.saar.maths.transform;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.saar.maths.utils.Matrix4;
import org.saar.maths.utils.Vector3;

import java.util.ArrayList;
import java.util.List;

public final class SimpleTransform implements Transform {

    private final Matrix4f transformation = Matrix4.create();

    private final Position position = Position.create();
    private final Rotation rotation = Rotation.create();
    private final Scale scale = Scale.create();

    private final List<Transform> transforms = new ArrayList<>();

    public SimpleTransform() {
        getPosition().addListener(e -> updateTransformationMatrix());
        getRotation().addListener(e -> updateTransformationMatrix());
        getScale().addListener(e -> updateTransformationMatrix());
    }

    private void updateTransformationMatrix() {
        Matrix4.ofTransformation(
                getPosition().getValue(),
                getRotation().getValue(),
                getScale().getValue(),
                this.transformation);

        for (Transform transform : this.transforms) {
            this.transformation.mul(transform
                    .getTransformationMatrix());
        }
    }

    public void addTransform(Transform transform) {
        this.transforms.add(transform);
        updateTransformationMatrix();
        transform.getPosition().addListener(e -> updateTransformationMatrix());
        transform.getRotation().addListener(e -> updateTransformationMatrix());
        transform.getScale().addListener(e -> updateTransformationMatrix());
    }

    @Override
    public Matrix4fc getTransformationMatrix() {
        return this.transformation;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Rotation getRotation() {
        return this.rotation;
    }

    @Override
    public Scale getScale() {
        return this.scale;
    }

    public void lookAt(Position position) {
        final Vector3f direction = Vector3.sub(
                position.getValue(), getPosition().getValue());
        getRotation().lookAlong(direction);
    }

    @Override
    public String toString() {
        return "Transform{" + position +
                ", " + rotation +
                ", " + scale + '}';
    }
}
