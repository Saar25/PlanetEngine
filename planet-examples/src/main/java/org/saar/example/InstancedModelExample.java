package org.saar.example;

import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.attribute.AttributeComposite;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.FboBlitFilter;
import org.saar.lwjgl.opengl.fbo.WindowFbo;
import org.saar.lwjgl.opengl.fbo.attachment.Attachment;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex;
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget;
import org.saar.lwjgl.opengl.fbo.rendertarget.RenderTarget;
import org.saar.rhi.opengl.shader.OpenglShaderProgram;
import org.saar.rhi.opengl.shader.OpenglShaderProgramKt;
import org.saar.rhi.shader.ShaderModule;
import org.saar.rhi.shader.ShaderProgram;
import org.saar.rhi.shader.ShaderStage;
import org.saar.rhi.shader.ShaderStageType;
import java.util.Arrays;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.lwjgl.opengl.vao.Vao;
import org.saar.lwjgl.opengl.vbo.DataBuffer;
import org.saar.lwjgl.opengl.vbo.VboUsage;
import org.saar.rhi.inputassembly.PrimitiveTopology;

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
        vao.loadVbo(dataBuffer, new AttributeComposite(
            Attributes.of(0, 2, DataType.FLOAT, true),
            Attributes.of(1, 3, DataType.FLOAT, true)
        ));
        dataBuffer.delete();

        final DataBuffer instanceBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        final float[] instanceData = {0.5f, .1f, .2f};
        instanceBuffer.allocateFloat(instanceData.length);
        instanceBuffer.storeFloat(0, instanceData);
        vao.loadVbo(instanceBuffer, Attributes.ofInstanced(2, 1, DataType.FLOAT, false));
        instanceBuffer.delete();

        final DrawCall drawCall = new InstancedArraysDrawCall(PrimitiveTopology.TRIANGLE_LIST, 3, 3);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);

        final ShaderModule vertexModule = ShaderModule.load("/vertex.glsl");
        final ShaderModule fragmentModule = ShaderModule.load("/fragment.glsl");
        final ShaderProgram shaderProgram = new ShaderProgram(Arrays.asList(
            new ShaderStage(vertexModule, ShaderStageType.VERTEX, "main"),
            new ShaderStage(fragmentModule, ShaderStageType.FRAGMENT, "main")
        ));
        final OpenglShaderProgram shadersProgram = OpenglShaderProgramKt.toOpengl(shaderProgram);
        OpenglShaderProgramKt.bindAttributes(shadersProgram, "in_position", "in_color");

        shadersProgram.bind();

        final Fbo fbo = Fbo.create();

        final AllocationStrategy allocation = SimpleAllocationStrategy.INSTANCE;
        final AttachmentBuffer buffer = new RenderBufferAttachmentBuffer(InternalFormat.RGBA8);
        final Attachment attachment = new Attachment(buffer, allocation);
        final AttachmentIndex attachmentIndex = ColorAttachmentIndex.at(0);
        final RenderTarget target = new IndexRenderTarget(attachmentIndex);

        attachment.allocate(WIDTH, HEIGHT);
        fbo.addAttachment(attachmentIndex, attachment);
        fbo.setReadTarget(target);
        fbo.setDrawTarget(target);

        GlUtils.setViewport(0, 0, WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            fbo.bind();
            GlUtils.clear(GlBuffer.COLOR);
            mesh.draw();

            WindowFbo.INSTANCE.bindAsDraw();
            GlUtils.clear(GlBuffer.COLOR);
            fbo.blitFramebuffer(WIDTH, HEIGHT, FboBlitFilter.LINEAR, GlBuffer.COLOR);

            window.swapBuffers();
            window.pollEvents();
        }

        fbo.delete();
        vao.delete();
        attachment.delete();
        window.destroy();
    }

}
