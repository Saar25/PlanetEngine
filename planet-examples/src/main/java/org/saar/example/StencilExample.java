package org.saar.example;

import org.saar.core.screen.MainScreen;
import org.saar.core.screen.Screen;
import org.saar.core.screen.SimpleScreen;
import org.saar.core.screen.image.ScreenImage;
import org.saar.core.screen.image.SimpleScreenImage;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.constants.Comparator;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.attachment.Attachment;
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.BasicAttachmentIndex;
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex;
import org.saar.lwjgl.opengl.shader.Shader;
import org.saar.lwjgl.opengl.shader.ShadersProgram;
import org.saar.lwjgl.opengl.stencil.*;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.lwjgl.opengl.vao.Vao;
import org.saar.lwjgl.opengl.vbo.DataBuffer;
import org.saar.lwjgl.opengl.vbo.VboUsage;

public class StencilExample {

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", 700, 500, true);

        final Vao vao1 = buildVao(0);
        final Vao vao2 = buildVao(0.3f);

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttribute(0, "in_position");

        shadersProgram.bind();

        final Screen screen = buildScreen(window.getWidth(), window.getHeight());

        StencilTest.enable();

        final StencilState writeStencil = new StencilState(
                new StencilOperation(StencilValue.KEEP, StencilValue.KEEP, StencilValue.REPLACE),
                new StencilFunction(Comparator.ALWAYS, 1, 0xFF), StencilMask.UNCHANGED);

        final StencilState readStencil = new StencilState(
                new StencilOperation(StencilValue.KEEP, StencilValue.KEEP, StencilValue.REPLACE),
                new StencilFunction(Comparator.EQUAL, 1, 0xFF), StencilMask.UNCHANGED);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            screen.setAsDraw();

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);

            StencilTest.apply(writeStencil);

            vao1.bind();
            vao1.enableAttributes();
            GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);

            StencilTest.apply(readStencil);

            vao2.bind();
            vao2.enableAttributes();
            GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);

            screen.copyTo(MainScreen.INSTANCE);

            window.swapBuffers();
            window.pollEvents();
        }

        window.destroy();
    }

    private static Screen buildScreen(int width, int height) {
        final Fbo fbo = Fbo.create(width, height);
        final SimpleScreen screen = new SimpleScreen(fbo);

        final AttachmentIndex stencilIndex = new BasicAttachmentIndex(AttachmentType.STENCIL);
        final AllocationStrategy stencilAllocation = new MultisampledAllocationStrategy(4);
        final AttachmentBuffer stencilBuffer = new RenderBufferAttachmentBuffer(InternalFormat.STENCIL_INDEX8);
        final Attachment stencilAttachment = new Attachment(stencilBuffer, stencilAllocation);
        final ScreenImage screenImage = new SimpleScreenImage(stencilAttachment);
        screen.addScreenImage(stencilIndex, screenImage);

        final AttachmentIndex colourIndex = new ColourAttachmentIndex(0);
        final AllocationStrategy colourAllocation = new MultisampledAllocationStrategy(4);
        final AttachmentBuffer colourBuffer = new RenderBufferAttachmentBuffer(InternalFormat.RGBA8);
        final Attachment attachment = new Attachment(colourBuffer, colourAllocation);
        final SimpleScreenImage colourImage = new SimpleScreenImage(attachment);
        screen.addScreenImage(colourIndex, colourImage);
        screen.setReadImages(colourIndex);
        screen.setDrawImages(colourIndex);

        fbo.ensureStatus();

        return screen;
    }

    private static Vao buildVao(float offset) {
        final Vao vao = Vao.create();

        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);

        final float[] data = {
                -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, offset,
                +0.0f, +0.5f, 0.0f, 1.0f, 0.0f, offset,
                +0.5f, -0.5f, 0.0f, 0.0f, 1.0f, offset
        };

        vbo.allocateFloat(data.length);
        vbo.storeFloat(0, data);

        vao.loadVbo(vbo,
                Attributes.of(0, 2, DataType.FLOAT, false),
                Attributes.of(1, 3, DataType.FLOAT, false),
                Attributes.of(2, 1, DataType.FLOAT, false));

        vbo.delete();

        return vao;
    }

}
