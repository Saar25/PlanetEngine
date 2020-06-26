package org.saar.example;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.Model;
import org.saar.core.model.data.FloatModelData;
import org.saar.core.model.data.IndexModelData;
import org.saar.core.model.data.ModelDataInfo;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class IndexedModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        /*final float[] positions = {
                -0.5f, -0.5f,
                -0.5f, +0.5f,
                +0.5f, +0.5f,
                +0.5f, -0.5f,
        };*/
        final float x = .5f;
        final float[] positions = {
                -x, -x,
                -x, +x,
                +x, +x,
                +x, -x,
        };
        final float[] colours = {
                +0.0f, +0.0f, +0.5f,
                +0.0f, +1.0f, +0.5f,
                +1.0f, +1.0f, +0.5f,
                +1.0f, +0.0f, +0.5f,
        };
        final Model model = new ElementsModel(RenderMode.TRIANGLES,
                new IndexModelData(0, 1, 2, 0, 2, 3),
                new FloatModelData(positions, new ModelDataInfo(2, true)),
                new FloatModelData(colours, new ModelDataInfo(3, true)));

        final ShadersProgram<Object> shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttribute(0, "in_position");
        shadersProgram.bindAttribute(1, "in_colour");

        shadersProgram.bind();

        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT);
        final RenderBufferAttachmentMS attachment = new RenderBufferAttachmentMS(
                AttachmentType.COLOUR, 0, RenderBuffer.create(), FormatType.BGRA, 16);
        fbo.addAttachment(attachment);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            model.draw();
            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }
    }

}
