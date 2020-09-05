package org.saar.example;

import org.saar.core.common.primitive.PrimitiveModelLoader;
import org.saar.core.common.primitive.PrimitiveNode;
import org.saar.core.common.primitive.PrimitiveVertex;
import org.saar.core.model.Model;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.colour.ColourAttachmentMS;
import org.saar.lwjgl.opengl.primitive.GlFloat;
import org.saar.lwjgl.opengl.primitive.GlFloat2;
import org.saar.lwjgl.opengl.primitive.GlFloat3;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class PrimitiveExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PrimitiveVertex[] vertices = {
                new PrimitiveVertex(GlFloat2.of(-0.5f, -0.5f), GlFloat3.of(+0.0f, +0.0f, +0.5f)),
                new PrimitiveVertex(GlFloat2.of(+0.0f, +0.5f), GlFloat3.of(+0.5f, +1.0f, +0.5f)),
                new PrimitiveVertex(GlFloat2.of(+0.5f, -0.5f), GlFloat3.of(+1.0f, +0.0f, +0.5f))
        };
        final PrimitiveNode[] nodes = new PrimitiveNode[]{
                new PrimitiveNode(GlFloat.of(+0.5f)),
                new PrimitiveNode(GlFloat.of(+0.1f)),
                new PrimitiveNode(GlFloat.of(+0.2f))
        };

        final Model model = new PrimitiveModelLoader(vertices, nodes).createModel();

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttributes("in_position", "in_colour");

        shadersProgram.bind();

        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT);
        final ColourAttachmentMS attachment = ColourAttachmentMS.withRenderBuffer(0, 16);
        fbo.addAttachment(attachment);
        fbo.setReadAttachment(attachment);
        fbo.setDrawAttachments(attachment);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            model.draw();
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }

        fbo.delete();
        model.delete();
        attachment.delete();
        window.destroy();
    }

}
