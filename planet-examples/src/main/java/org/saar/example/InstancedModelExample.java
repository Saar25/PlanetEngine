package org.saar.example;

import org.saar.core.model.InstancedArraysMesh;
import org.saar.core.model.Mesh;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class InstancedModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final Vao vao = Vao.create();

        final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        final float[] data = {
                -0.5f, -0.5f, +0.0f, +0.0f, +0.5f,
                +0.0f, +0.5f, +0.5f, +1.0f, +0.5f,
                +0.5f, -0.5f, +1.0f, +0.0f, +0.5f};
        dataBuffer.allocateFloat(data.length);
        dataBuffer.storeData(0, data);
        vao.loadVbo(dataBuffer,
                Attribute.of(0, 2, DataType.FLOAT, true),
                Attribute.of(1, 3, DataType.FLOAT, true));

        final DataBuffer instanceBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        final float[] instanceData = {0.5f, .1f, .2f};
        instanceBuffer.allocateFloat(instanceData.length);
        instanceBuffer.storeData(0, instanceData);
        vao.loadVbo(instanceBuffer, Attribute.ofInstance(2, 1, DataType.FLOAT, false));

        final Mesh mesh = new InstancedArraysMesh(vao, RenderMode.TRIANGLES, 3, 3);

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
        vao.delete();
        attachment.delete();
        window.destroy();
    }

}
