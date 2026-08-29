package org.saar.example;

import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.attribute.AttributeComposite;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.FboBlitFilter;
import org.saar.lwjgl.opengl.fbo.WindowFbo;
import org.saar.lwjgl.opengl.fbo.attachment.Attachment;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy;
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
import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.lwjgl.opengl.vao.Vao;
import org.saar.lwjgl.opengl.vbo.DataBuffer;
import org.saar.lwjgl.opengl.vbo.VboUsage;
import org.saar.rhi.inputassembly.PrimitiveTopology;

public class MultisamplingExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final Vao vao = Vao.create();
        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateFloat(18);
        vbo.storeFloat(0, new float[]{
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f, 0.0f,
            +0.0f, +0.5f, 1.0f, 1.0f, 1.0f, 0.0f,
            +0.5f, -0.5f, 1.0f, 1.0f, 1.0f, 0.0f});
        vao.loadVbo(vbo, new AttributeComposite(
            Attributes.of(0, 2, DataType.FLOAT, false),
            Attributes.of(1, 3, DataType.FLOAT, false),
            Attributes.of(2, 1, DataType.FLOAT, false)
        ));
        vbo.delete();

        final ShaderModule vertexModule = ShaderModule.load("/vertex.glsl");
        final ShaderModule fragmentModule = ShaderModule.load("/fragment.glsl");
        final ShaderProgram shaderProgram = new ShaderProgram(Arrays.asList(
            new ShaderStage(vertexModule, ShaderStageType.VERTEX, "main"),
            new ShaderStage(fragmentModule, ShaderStageType.FRAGMENT, "main")
        ));
        final OpenglShaderProgram shadersProgram = OpenglShaderProgramKt.toOpengl(shaderProgram);
        shadersProgram.bindAttribute(0, "in_position");

        shadersProgram.bind();

        vao.bind();

        final Fbo fbo = Fbo.create();

        final AllocationStrategy allocation = new MultisampledAllocationStrategy(8);
        final AttachmentBuffer buffer = new RenderBufferAttachmentBuffer(InternalFormat.RGBA8);
        final AttachmentIndex attachmentIndex = ColorAttachmentIndex.at(0);
        final Attachment attachment = new Attachment(buffer, allocation);
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
            GlRendering.drawArrays(PrimitiveTopology.TRIANGLE_LIST, 0, 3);

            WindowFbo.INSTANCE.bindAsDraw();
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
