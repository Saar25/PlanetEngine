package org.saar.core.camera;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.behavior.BehaviorGroup;
import org.saar.core.behavior.BehaviorNode;
import org.saar.core.common.behaviors.TransformBehavior;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.utils.Matrix4;

public class Camera implements ICamera, BehaviorNode {

    private final Matrix4f viewMatrix = Matrix4.create();
    private final SimpleTransform transform = new SimpleTransform();
    private final Projection projection;

    private final BehaviorGroup behaviors;

    public Camera(Projection projection) {
        this.projection = projection;
        this.behaviors = new BehaviorGroup();
    }

    public Camera(Projection projection, BehaviorGroup behaviors) {
        this.projection = projection;
        this.behaviors = new BehaviorGroup(behaviors,
                new TransformBehavior(this.transform));
        this.behaviors.start(this);
    }

    @Override
    public Matrix4fc getViewMatrix() {
        return Matrix4.ofView(
                getTransform().getPosition().getValue(),
                getTransform().getRotation().getValue(),
                this.viewMatrix);
    }

    @Override
    public SimpleTransform getTransform() {
        return this.transform;
    }

    @Override
    public Projection getProjection() {
        return this.projection;
    }

    @Override
    public BehaviorGroup getBehaviors() {
        return this.behaviors;
    }

    @Override
    public void update() {
        getBehaviors().update(this);
    }

    @Override
    public void delete() {
        getBehaviors().delete(this);
    }
}
