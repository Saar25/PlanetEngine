package org.saar.core.renderer.basic;

import org.saar.core.node.GroupNode;
import org.saar.core.renderer.AbstractRenderer;
import org.saar.core.renderer.Renderer;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class BasicRenderer extends AbstractRenderer implements Renderer {

    private final GroupNode<BasicNode> renderNode = new GroupNode<>();

    public BasicRenderer(ShadersProgram shadersProgram) {
        super(shadersProgram);
    }

    @Override
    public GroupNode<BasicNode> getRenderNode() {
        return this.renderNode;
    }

    @Override
    public void render() {

    }

    @Override
    public void delete() {

    }
}
