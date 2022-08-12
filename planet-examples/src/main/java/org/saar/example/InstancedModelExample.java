package org.saar.example;

import org.saar.core.mesh.InstancedArraysMesh;
import org.saar.core.mesh.Mesh;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.vao.Vao;
import org.saar.lwjgl.opengl.vbo.DataBuffer;
import org.saar.lwjgl.opengl.vbo.VboUsage;
import org.saar.lwjgl.opengl.shader.Shader;
import org.saar.lwjgl.opengl.shader.ShadersProgram;

public class InstancedModelExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final Vao vao = Vao.create();

        final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        final float[] data = {
                -0.5f, -0.5f, +0.0f, +0.0f, +0.5f,
                +0.0f, +0.5f, +0.5f, +1.0f, +0.5f,
                +0.5f, -0.5f, +1.0f, +0.0f, +0.5f};
        dataBuffer.allocateFloat(data.length);
        dataBuffer.storeFloat(0, data);
        vao.loadVbo(dataBuffer,
                Attributes.of(0, 2, DataType.FLOAT, true),
                Attributes.of(1, 3, DataType.FLOAT, true));
        dataBuffer.delete();

        final DataBuffer instanceBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        final float[] instanceData = {0.5f, .1f, .2f};
        instanceBuffer.allocateFloat(instanceData.length);
        instanceBuffer.storeFloat(0, instanceData);
        vao.loadVbo(instanceBuffer, Attributes.ofInstanced(2, 1, DataType.FLOAT, false));
        instanceBuffer.delete();

        final Mesh mesh = new InstancedArraysMesh(vao,
                new InstancedArraysDrawCall(RenderMode.TRIANGLES, 3, 3));

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttributes("in_position", "in_colour");

        shadersProgram.bind();

        final Fbo fbo = Fbo.create(WIDTH, HEIGHT);

        final AllocationStrategy allocation = new SimpleAllocationStrategy();
        final AttachmentBuffer buffer = new RenderBufferAttachmentBuffer(InternalFormat.RGBA8);
        final ColourAttachment attachment = new ColourAttachment(0, buffer, allocation);

        fbo.addAttachment(attachment);
        fbo.setReadAttachment(attachment);
        fbo.setDrawAttachments(attachment);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();
            mesh.draw();
            fbo.blitToScreen();

            window.swapBuffers();
            window.pollEvents();
        }

        fbo.delete();
        vao.delete();
        attachment.delete();
        window.destroy();
    }

}
