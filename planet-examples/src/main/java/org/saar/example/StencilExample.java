package org.saar.example;

import org.saar.core.screen.MainScreen;
import org.saar.core.screen.Screen;
import org.saar.core.screen.SimpleScreen;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.DepthStencilScreenImage;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.*;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthStencilAttachment;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.stencil.*;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.lwjgl.opengl.utils.GlUtils;

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

            screen.copyTo(MainScreen.getInstance());

            window.update(true);
            window.pollEvents();
        }

        window.destroy();
    }

    private static Screen buildScreen(int width, int height) {
        final SimpleScreen screen = new SimpleScreen(Fbo.create(width, height));

        final DepthStencilScreenImage depthStencilImage = new DepthStencilScreenImage(DepthStencilAttachment.withTexture(
                Texture.create(TextureTarget.TEXTURE_2D), DepthStencilFormatType.DEPTH24_STENCIL8, DataType.U_INT_24_8
        ));
        screen.addScreenImage(depthStencilImage);

        final ColourScreenImage colourImage = new ColourScreenImage(ColourAttachment.withTexture(0,
                Texture.create(TextureTarget.TEXTURE_2D), ColourFormatType.RGBA8, FormatType.RGBA, DataType.BYTE));
        screen.addScreenImage(colourImage);
        screen.setReadImages(colourImage);
        screen.setDrawImages(colourImage);

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
