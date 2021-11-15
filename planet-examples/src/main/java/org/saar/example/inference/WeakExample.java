package org.saar.example.inference;

import org.saar.core.common.inference.weak.WeakInference;
import org.saar.core.common.inference.weak.WeakInstance;
import org.saar.core.common.inference.weak.WeakMesh;
import org.saar.core.common.inference.weak.WeakVertex;
import org.saar.core.mesh.Mesh;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.primitive.GlFloat;
import org.saar.lwjgl.opengl.primitive.GlFloat2;
import org.saar.lwjgl.opengl.primitive.GlFloat3;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class WeakExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        final Mesh mesh = buildMesh();

        final ShadersProgram shadersProgram = ShadersProgram.create(
                Shader.createVertex("/vertex.glsl"),
                Shader.createFragment("/fragment.glsl"));
        shadersProgram.bindAttributes("in_position", "in_colour", "in_offset");

        shadersProgram.bind();

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {

            mesh.draw();

            window.swapBuffers();
            window.pollEvents();
        }

        mesh.delete();
        window.destroy();
    }

    private static Mesh buildMesh() {
        final WeakVertex[] vertices = {
                WeakInference.vertex(GlFloat2.of(-0.5f, -0.5f), GlFloat3.of(+0.0f, +0.0f, +0.5f)),
                WeakInference.vertex(GlFloat2.of(+0.0f, +0.5f), GlFloat3.of(+0.5f, +1.0f, +0.5f)),
                WeakInference.vertex(GlFloat2.of(+0.5f, -0.5f), GlFloat3.of(+1.0f, +0.0f, +0.5f))
        };
        final WeakInstance[] nodes = {
                WeakInference.instance(GlFloat.of(+0.5f)),
                WeakInference.instance(GlFloat.of(+0.1f)),
                WeakInference.instance(GlFloat.of(+0.2f))
        };

        return WeakMesh.load(vertices, nodes);
    }
}
