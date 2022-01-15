package org.saar.maths.transform;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.jproperty.binding.ObjectBinding;
import org.saar.maths.utils.Matrix4;
import org.saar.maths.utils.Vector3;

public final class SimpleTransform implements Transform {

    private final Position position = Position.create();
    private final Rotation rotation = Rotation.create();
    private final Scale scale = Scale.create();

    private final ObjectBinding<Matrix4f> transformation = new ObjectBinding<>(this.position, this.rotation, this.scale) {
        @Override
        protected Matrix4f compute() {
            return Matrix4.ofTransformation(
                    getPosition().getValue(),
                    getRotation().getValue(),
                    getScale().getValue(),
                    Matrix4.create());
        }
    };

    @Override
    public Matrix4fc getTransformationMatrix() {
        return this.transformation.getValue();
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
