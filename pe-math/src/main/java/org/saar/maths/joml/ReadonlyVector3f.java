package org.saar.maths.joml;

import org.joml.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class ReadonlyVector3f implements Vector3fc {

    private final Vector3fc value;

    public ReadonlyVector3f(Vector3fc value) {
        this.value = value;
    }

    @Override
    public float x() {
        return this.value.x();
    }

    @Override
    public float y() {
        return this.value.y();
    }

    @Override
    public float z() {
        return this.value.z();
    }

    @Override
    public FloatBuffer get(FloatBuffer floatBuffer) {
        return this.value.get(floatBuffer);
    }

    @Override
    public FloatBuffer get(int i, FloatBuffer floatBuffer) {
        return this.value.get(i, floatBuffer);
    }

    @Override
    public ByteBuffer get(ByteBuffer byteBuffer) {
        return this.value.get(byteBuffer);
    }

    @Override
    public ByteBuffer get(int i, ByteBuffer byteBuffer) {
        return this.value.get(i, byteBuffer);
    }

    @Override
    public Vector3fc getToAddress(long l) {
        return this.value.getToAddress(l);
    }

    @Override
    public Vector3f sub(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.sub(vector3fc, vector3f);
    }

    @Override
    public Vector3f sub(float v, float v1, float v2, Vector3f vector3f) {
        return this.value.sub(v, v1, v2, vector3f);
    }

    @Override
    public Vector3f add(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.add(vector3fc, vector3f);
    }

    @Override
    public Vector3f add(float v, float v1, float v2, Vector3f vector3f) {
        return this.value.add(v, v1, v2, vector3f);
    }

    @Override
    public Vector3f fma(Vector3fc vector3fc, Vector3fc vector3fc1, Vector3f vector3f) {
        return this.value.fma(vector3fc, vector3fc1, vector3f);
    }

    @Override
    public Vector3f fma(float v, Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.fma(v, vector3fc, vector3f);
    }

    @Override
    public Vector3f mul(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.mul(vector3fc, vector3f);
    }

    @Override
    public Vector3f div(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.div(vector3fc, vector3f);
    }

    @Override
    public Vector3f mulProject(Matrix4fc matrix4fc, Vector3f vector3f) {
        return this.value.mulProject(matrix4fc, vector3f);
    }

    @Override
    public Vector3f mulProject(Matrix4fc matrix4fc, float v, Vector3f vector3f) {
        return this.value.mulProject(matrix4fc, v, vector3f);
    }

    @Override
    public Vector3f mul(Matrix3fc matrix3fc, Vector3f vector3f) {
        return this.value.mul(matrix3fc, vector3f);
    }

    @Override
    public Vector3f mul(Matrix3dc matrix3dc, Vector3f vector3f) {
        return this.value.mul(matrix3dc, vector3f);
    }

    @Override
    public Vector3f mul(Matrix3x2fc matrix3x2fc, Vector3f vector3f) {
        return this.value.mul(matrix3x2fc, vector3f);
    }

    @Override
    public Vector3f mulTranspose(Matrix3fc matrix3fc, Vector3f vector3f) {
        return this.value.mulTranspose(matrix3fc, vector3f);
    }

    @Override
    public Vector3f mulPosition(Matrix4fc matrix4fc, Vector3f vector3f) {
        return this.value.mulPosition(matrix4fc, vector3f);
    }

    @Override
    public Vector3f mulPosition(Matrix4x3fc matrix4x3fc, Vector3f vector3f) {
        return this.value.mulPosition(matrix4x3fc, vector3f);
    }

    @Override
    public Vector3f mulTransposePosition(Matrix4fc matrix4fc, Vector3f vector3f) {
        return this.value.mulTransposePosition(matrix4fc, vector3f);
    }

    @Override
    public float mulPositionW(Matrix4fc matrix4fc, Vector3f vector3f) {
        return this.value.mulPositionW(matrix4fc, vector3f);
    }

    @Override
    public Vector3f mulDirection(Matrix4dc matrix4dc, Vector3f vector3f) {
        return this.value.mulDirection(matrix4dc, vector3f);
    }

    @Override
    public Vector3f mulDirection(Matrix4fc matrix4fc, Vector3f vector3f) {
        return this.value.mulDirection(matrix4fc, vector3f);
    }

    @Override
    public Vector3f mulDirection(Matrix4x3fc matrix4x3fc, Vector3f vector3f) {
        return this.value.mulDirection(matrix4x3fc, vector3f);
    }

    @Override
    public Vector3f mulTransposeDirection(Matrix4fc matrix4fc, Vector3f vector3f) {
        return this.value.mulTransposeDirection(matrix4fc, vector3f);
    }

    @Override
    public Vector3f mul(float v, Vector3f vector3f) {
        return this.value.mul(v, vector3f);
    }

    @Override
    public Vector3f mul(float v, float v1, float v2, Vector3f vector3f) {
        return this.value.mul(v, v1, v2, vector3f);
    }

    @Override
    public Vector3f div(float v, Vector3f vector3f) {
        return this.value.div(v, vector3f);
    }

    @Override
    public Vector3f div(float v, float v1, float v2, Vector3f vector3f) {
        return this.value.div(v, v1, v2, vector3f);
    }

    @Override
    public Vector3f rotate(Quaternionfc quaternionfc, Vector3f vector3f) {
        return this.value.rotate(quaternionfc, vector3f);
    }

    @Override
    public Quaternionf rotationTo(Vector3fc vector3fc, Quaternionf quaternionf) {
        return this.value.rotationTo(vector3fc, quaternionf);
    }

    @Override
    public Quaternionf rotationTo(float v, float v1, float v2, Quaternionf quaternionf) {
        return this.value.rotationTo(v, v1, v2, quaternionf);
    }

    @Override
    public Vector3f rotateAxis(float v, float v1, float v2, float v3, Vector3f vector3f) {
        return this.value.rotateAxis(v, v1, v2, v3, vector3f);
    }

    @Override
    public Vector3f rotateX(float v, Vector3f vector3f) {
        return this.value.rotateX(v, vector3f);
    }

    @Override
    public Vector3f rotateY(float v, Vector3f vector3f) {
        return this.value.rotateY(v, vector3f);
    }

    @Override
    public Vector3f rotateZ(float v, Vector3f vector3f) {
        return this.value.rotateZ(v, vector3f);
    }

    @Override
    public float lengthSquared() {
        return this.value.lengthSquared();
    }

    @Override
    public float length() {
        return this.value.length();
    }

    @Override
    public Vector3f normalize(Vector3f vector3f) {
        return this.value.normalize(vector3f);
    }

    @Override
    public Vector3f normalize(float v, Vector3f vector3f) {
        return this.value.normalize(v, vector3f);
    }

    @Override
    public Vector3f cross(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.cross(vector3fc, vector3f);
    }

    @Override
    public Vector3f cross(float v, float v1, float v2, Vector3f vector3f) {
        return this.value.cross(v, v1, v2, vector3f);
    }

    @Override
    public float distance(Vector3fc vector3fc) {
        return this.value.distance(vector3fc);
    }

    @Override
    public float distance(float v, float v1, float v2) {
        return this.value.distance(v, v1, v2);
    }

    @Override
    public float distanceSquared(Vector3fc vector3fc) {
        return this.value.distanceSquared(vector3fc);
    }

    @Override
    public float distanceSquared(float v, float v1, float v2) {
        return this.value.distanceSquared(v, v1, v2);
    }

    @Override
    public float dot(Vector3fc vector3fc) {
        return this.value.dot(vector3fc);
    }

    @Override
    public float dot(float v, float v1, float v2) {
        return this.value.dot(v, v1, v2);
    }

    @Override
    public float angleCos(Vector3fc vector3fc) {
        return this.value.angleCos(vector3fc);
    }

    @Override
    public float angle(Vector3fc vector3fc) {
        return this.value.angle(vector3fc);
    }

    @Override
    public float angleSigned(Vector3fc vector3fc, Vector3fc vector3fc1) {
        return this.value.angleSigned(vector3fc, vector3fc1);
    }

    @Override
    public float angleSigned(float v, float v1, float v2, float v3, float v4, float v5) {
        return this.value.angleSigned(v, v1, v2, v3, v4, v5);
    }

    @Override
    public Vector3f min(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.min(vector3fc, vector3f);
    }

    @Override
    public Vector3f max(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.max(vector3fc, vector3f);
    }

    @Override
    public Vector3f negate(Vector3f vector3f) {
        return this.value.negate(vector3f);
    }

    @Override
    public Vector3f absolute(Vector3f vector3f) {
        return this.value.absolute(vector3f);
    }

    @Override
    public Vector3f reflect(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.reflect(vector3fc, vector3f);
    }

    @Override
    public Vector3f reflect(float v, float v1, float v2, Vector3f vector3f) {
        return this.value.reflect(v, v1, v2, vector3f);
    }

    @Override
    public Vector3f half(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.half(vector3fc, vector3f);
    }

    @Override
    public Vector3f half(float v, float v1, float v2, Vector3f vector3f) {
        return this.value.half(v, v1, v2, vector3f);
    }

    @Override
    public Vector3f smoothStep(Vector3fc vector3fc, float v, Vector3f vector3f) {
        return this.value.smoothStep(vector3fc, v, vector3f);
    }

    @Override
    public Vector3f hermite(Vector3fc vector3fc, Vector3fc vector3fc1, Vector3fc vector3fc2, float v, Vector3f vector3f) {
        return this.value.hermite(vector3fc, vector3fc1, vector3fc2, v, vector3f);
    }

    @Override
    public Vector3f lerp(Vector3fc vector3fc, float v, Vector3f vector3f) {
        return this.value.lerp(vector3fc, v, vector3f);
    }

    @Override
    public float get(int i) throws IllegalArgumentException {
        return this.value.get(i);
    }

    @Override
    public Vector3f get(Vector3f vector3f) {
        return this.value.get(vector3f);
    }

    @Override
    public Vector3d get(Vector3d vector3d) {
        return this.value.get(vector3d);
    }

    @Override
    public int maxComponent() {
        return this.value.maxComponent();
    }

    @Override
    public int minComponent() {
        return this.value.minComponent();
    }

    @Override
    public Vector3f orthogonalize(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.orthogonalize(vector3fc, vector3f);
    }

    @Override
    public Vector3f orthogonalizeUnit(Vector3fc vector3fc, Vector3f vector3f) {
        return this.value.orthogonalizeUnit(vector3fc, vector3f);
    }

    @Override
    public Vector3f floor(Vector3f vector3f) {
        return this.value.floor(vector3f);
    }

    @Override
    public Vector3f ceil(Vector3f vector3f) {
        return this.value.ceil(vector3f);
    }

    @Override
    public Vector3f round(Vector3f vector3f) {
        return this.value.round(vector3f);
    }

    @Override
    public boolean isFinite() {
        return this.value.isFinite();
    }

    @Override
    public boolean equals(Vector3fc vector3fc, float v) {
        return this.value.equals(vector3fc, v);
    }

    @Override
    public boolean equals(float v, float v1, float v2) {
        return this.value.equals(v, v1, v2);
    }
}
