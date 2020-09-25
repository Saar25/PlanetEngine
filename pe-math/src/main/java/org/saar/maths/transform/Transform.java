package org.saar.maths.transform;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.Angle;
import org.saar.maths.utils.Matrix4;
import org.saar.maths.utils.Quaternion;
import org.saar.maths.utils.Vector3;

public final class Transform {

    private final Matrix4f transformation = Matrix4.create();

    private final Position position = Position.create();
    private final Rotation rotation = Rotation.create();
    private final Scale scale = Scale.create();

    public Transform() {

    }

    public Matrix4f getTransformationMatrix() {
        return Matrix4.ofTransformation(
                getPosition().getValue(),
                getRotation().getValue(),
                getScale().getValue(),
                this.transformation);
    }

    public Position getPosition() {
        return this.position;
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public Scale getScale() {
        return this.scale;
    }

    public void setRotation(Angle x, Angle y, Angle z) {
        final Quaternionf q = Quaternion.create().rotateXYZ(
                x.getRadians(), y.getRadians(), z.getRadians());
        getRotation().set(Rotation.fromQuaternion(q));
    }

    public void addRotation(Angle x, Angle y, Angle z) {
        final Quaternionf quaternion = Quaternion.of(getRotation().getValue())
                .rotateX(x.getRadians())
                .rotateLocalY(y.getRadians())
                .rotateZ(z.getRadians());
        getRotation().set(Rotation.fromQuaternion(quaternion));
    }

    public void setRotation(float x, float y, float z) {
        final Quaternionf quaternion = Quaternion.create().rotateXYZ(
                (float) Math.toRadians(x),
                (float) Math.toRadians(y),
                (float) Math.toRadians(z));
        getRotation().set(Rotation.fromQuaternion(quaternion));
    }

    public void lookAlong(Vector3fc direction) {
        direction = Vector3.normalize(direction);
        if (direction.equals(Vector3.DOWN, 0)) {
            final Quaternionf quaternion = Quaternion.of(-1, 0, 0, 1).normalize();
            getRotation().set(Rotation.fromQuaternion(quaternion));
        } else if (!direction.equals(Vector3.ZERO, 0)) {
            final Quaternionf quaternion = Quaternion.create()
                    .lookAlong(direction, Vector3.UP).conjugate();
            getRotation().set(Rotation.fromQuaternion(quaternion));
        }
    }

    public void lookAt(Position position) {
        final Vector3f direction = Vector3.sub(
                position.getValue(), getPosition().getValue());
        lookAlong(direction);
    }

    public Vector3f getDirection() {
        return Vector3.forward().rotate(getRotation().getValue());
    }

    @Override
    public String toString() {
        return "Transform{" + position +
                ", " + rotation +
                ", " + scale + '}';
    }
}
