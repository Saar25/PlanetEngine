package org.saar.example;

import org.saar.core.screen.MainScreen;
import org.saar.core.screen.Screen;
import org.saar.core.screen.ScreenBuilder;
import org.saar.core.screen.ScreenKt;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.attribute.AttributeComposite;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.IFbo;
import org.saar.lwjgl.opengl.shader.Shader;
import org.saar.lwjgl.opengl.shader.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.lwjgl.opengl.vao.Vao;
import org.saar.lwjgl.opengl.vbo.DataBuffer;
import org.saar.lwjgl.opengl.vbo.VboUsage;
import org.saar.rhi.depthstencil.CompareOp;
import org.saar.rhi.depthstencil.DepthStencilStateKt;
import org.saar.rhi.depthstencil.StencilOp;
import org.saar.rhi.depthstencil.StencilOpState;
import org.saar.rhi.inputassembly.PrimitiveTopology;
import org.saar.rhi.opengl.depthstencil.OpenglDepthStencilState;

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

        final OpenglDepthStencilState writeDepthStencilState = new OpenglDepthStencilState(
            DepthStencilStateKt.DepthStencilState(
                null,
                null,
                null,
                null,
                true,
                new StencilOpState(
                    StencilOp.KEEP,
                    StencilOp.REPLACE,
                    StencilOp.KEEP,
                    CompareOp.ALWAYS,
                    0xFF,
                    -1,
                    1
                ),
                null,
                null
            )
        );
        final OpenglDepthStencilState readDepthStencilState = new OpenglDepthStencilState(
            DepthStencilStateKt.DepthStencilState(
                null,
                null,
                null,
                null,
                true,
                new StencilOpState(
                    StencilOp.KEEP,
                    StencilOp.REPLACE,
                    StencilOp.KEEP,
                    CompareOp.EQUAL,
                    0xFF,
                    -1,
                    1
                ),
                null,
                null
            )
        );

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            screen.setAsDraw();

            GlUtils.clear(GlBuffer.COLOR, GlBuffer.DEPTH, GlBuffer.STENCIL);

            writeDepthStencilState.set();

            vao1.bind();
            GlRendering.drawArrays(PrimitiveTopology.TRIANGLE_LIST, 0, 3);

            readDepthStencilState.set();

            vao2.bind();
            GlRendering.drawArrays(PrimitiveTopology.TRIANGLE_LIST, 0, 3);

            ScreenKt.copyTo(screen, MainScreen.INSTANCE);

            window.swapBuffers();
            window.pollEvents();
        }

        window.destroy();
    }

    private static Screen buildScreen(int width, int height) {
        final IFbo fbo = Fbo.create();

        return new ScreenBuilder(fbo)
            .addColorRenderBuffer(InternalFormat.RGBA8).setRead()
            .addStencilRenderBuffer(InternalFormat.STENCIL_INDEX8)
            .multisampled(4)
            .build(width, height);
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

        vao.loadVbo(vbo, new AttributeComposite(
            Attributes.of(0, 2, DataType.FLOAT, false),
            Attributes.of(1, 3, DataType.FLOAT, false),
            Attributes.of(2, 1, DataType.FLOAT, false)
        ));

        vbo.delete();

        return vao;
    }

}
