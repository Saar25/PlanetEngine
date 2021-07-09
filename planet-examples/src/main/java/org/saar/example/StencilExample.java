package org.saar.example;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
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

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.STENCIL);

            GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
            GL11.glStencilMask(0xFF);

            vao1.bind();
            vao1.enableAttributes();
            GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);

            GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
            GL11.glStencilMask(0x00);

            vao2.bind();
            vao2.enableAttributes();
            GlRendering.drawArrays(RenderMode.TRIANGLES, 0, 3);

            window.update(true);
            window.pollEvents();
        }

        window.destroy();
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
