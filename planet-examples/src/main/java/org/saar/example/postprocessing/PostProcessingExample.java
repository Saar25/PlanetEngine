package org.saar.example.postprocessing;

import org.saar.core.common.inference.weak.WeakInference;
import org.saar.core.common.inference.weak.WeakMesh;
import org.saar.core.common.inference.weak.WeakVertex;
import org.saar.core.mesh.Mesh;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.primitive.GlFloat2;
import org.saar.lwjgl.opengl.primitive.GlFloat3;
import org.saar.lwjgl.opengl.shaders.GlslVersion;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShaderCode;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class PostProcessingExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        GlUtils.setClearColour(.2f, .2f, .2f);

        final WeakVertex[] vertices = {
                WeakInference.vertex(GlFloat2.of(-0.5f, -0.5f), GlFloat3.of(+0.0f, +0.0f, +0.5f)),
                WeakInference.vertex(GlFloat2.of(+0.0f, +0.5f), GlFloat3.of(+0.5f, +1.0f, +0.5f)),
                WeakInference.vertex(GlFloat2.of(+0.5f, -0.5f), GlFloat3.of(+1.0f, +0.0f, +0.5f))
        };

        final Mesh mesh = WeakMesh.load(vertices);

        final Shader vertexShader = Shader.createVertex(GlslVersion.V400,
                ShaderCode.loadSource("/simple.vertex.glsl"));
        final Shader fragmentShader = Shader.createFragment(GlslVersion.V400,
                ShaderCode.loadSource("/simple.fragment.glsl"));
        final ShadersProgram shadersProgram = ShadersProgram.create(vertexShader, fragmentShader);

        shadersProgram.bindAttributes("in_position", "in_colour", "in_offset");

        shadersProgram.bind();

        final MultisampledFbo fbo = new MultisampledFbo(WIDTH, HEIGHT, 16);
        final ColourAttachment attachment = ColourAttachment.withRenderBuffer(0, ColourFormatType.RGBA8);
        fbo.addAttachment(attachment);
        fbo.setReadAttachment(attachment);
        fbo.setDrawAttachments(attachment);

        window.addResizeListener(e -> fbo.resize(
                e.getWidth().getAfter(), e.getHeight().getAfter()));

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            fbo.bind();

            GlUtils.clearColourAndDepthBuffer();
            mesh.draw();

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
        }

        fbo.delete();
        mesh.delete();
        window.destroy();
    }

}
