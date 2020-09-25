package org.saar.maths.transform;

import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.saar.maths.utils.Matrix4;
import org.saar.maths.utils.Vector3;
import org.saar.maths.wrapper.Matrix4fWrapper;

public final class SimpleTransform implements Transform {

    private final Matrix4fWrapper transformation = new Matrix4fWrapper();

    private final Position position = Position.create();
    private final Rotation rotation = Rotation.create();
    private final Scale scale = Scale.create();

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
                this.transformation.getValue());
    }

    @Override
    public Matrix4fc getTransformationMatrix() {
        return this.transformation.getReadonly();
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
