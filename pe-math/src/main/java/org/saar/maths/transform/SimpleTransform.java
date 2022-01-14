package org.saar.maths.transform;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.jproperty.Subscription;
import org.saar.maths.utils.Matrix4;
import org.saar.maths.utils.Vector3;

import java.util.Arrays;
import java.util.List;

public final class SimpleTransform implements Transform {

    private final Matrix4f transformation = Matrix4.create();

    private final List<Subscription> subscriptions;

    private final Position position = Position.create();
    private final Rotation rotation = Rotation.create();
    private final Scale scale = Scale.create();

    public SimpleTransform() {
        this.subscriptions = Arrays.asList(
                getPosition().subscribe(e -> updateTransformationMatrix()),
                getRotation().subscribe(e -> updateTransformationMatrix()),
                getScale().subscribe(e -> updateTransformationMatrix())
        );
    }

    private void updateTransformationMatrix() {
        Matrix4.ofTransformation(
                getPosition().getValue(),
                getRotation().getValue(),
                getScale().getValue(),
                this.transformation);
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
