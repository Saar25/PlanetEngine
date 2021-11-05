package org.saar.core.camera;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.node.ComposableNode;
import org.saar.core.common.components.TransformComponent;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.utils.Matrix4;

public class Camera implements ICamera, ComposableNode {

    private final Matrix4f viewMatrix = Matrix4.create();
    private final SimpleTransform transform = new SimpleTransform();
    private final Projection projection;

    private final NodeComponentGroup components;

    public Camera(Projection projection) {
        this.projection = projection;
        this.components = new NodeComponentGroup();
    }

    public Camera(Projection projection, NodeComponentGroup components) {
        this.projection = projection;
        this.components = new NodeComponentGroup(components,
                new TransformComponent(this.transform));
        this.components.start(this);
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
    public NodeComponentGroup getComponents() {
        return this.components;
    }

    @Override
    public void update() {
        getComponents().update(this);
    }

    @Override
    public void delete() {
        getComponents().delete(this);
    }
}
