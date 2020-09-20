package org.saar.example;

import org.saar.core.common.simple.SimpleBufferWriter;
import org.saar.core.common.simple.SimpleVertex;
import org.saar.core.model.Mesh;
import org.saar.core.model.Meshes;
import org.saar.core.model.vertex.ModelAttribute;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

import java.util.Arrays;
import java.util.List;

public class ModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final List<SimpleVertex> vertices = Arrays.asList(
                new SimpleVertex(-0.5f, -0.5f, +0.0f, +0.0f, +0.5f),
                new SimpleVertex(+0.0f, +0.5f, +0.5f, +1.0f, +0.5f),
                new SimpleVertex(+0.5f, -0.5f, +1.0f, +0.0f, +0.5f));
        final SimpleBufferWriter writer = new SimpleBufferWriter(
                new ModelAttribute(2, true, DataType.FLOAT),
                new ModelAttribute(3, true, DataType.FLOAT));
        final Mesh mesh = Meshes.arraysModel(RenderMode.TRIANGLE_STRIP, writer, vertices);

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttributes("in_position", "in_colour");

        shadersProgram.bind();

        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT, 16);
        final ColourAttachment attachment = ColourAttachment.withRenderBuffer(0, InternalFormat.RGBA8);
        fbo.addAttachment(attachment);
        fbo.setReadAttachment(attachment);
        fbo.setDrawAttachments(attachment);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            mesh.draw();
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }

        fbo.delete();
        mesh.delete();
        attachment.delete();
        window.destroy();
    }

}
