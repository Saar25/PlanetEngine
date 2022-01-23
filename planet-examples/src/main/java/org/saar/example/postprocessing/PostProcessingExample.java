package org.saar.example.postprocessing;

import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.NullProjection;
import org.saar.core.common.r2d.*;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.GaussianBlurPostProcessor;
import org.saar.core.renderer.p2d.GeometryPass2D;
import org.saar.core.renderer.p2d.RenderingPath2D;
import org.saar.core.renderer.p2d.RenderingPipeline2D;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

public class PostProcessingExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(.2f, .2f, .2f);

        final Camera camera = new Camera(NullProjection.getInstance());

        final Model2D model = buildModel2D();
        final Node2D node = new Node2D(model);

        final RenderingPipeline2D pipeline = new RenderingPipeline2D(
                new GeometryPass2D(node),
                new ContrastPostProcessor(1.8f),
                new GaussianBlurPostProcessor(11, 2)
        );

        final RenderingPath2D renderingPath = new RenderingPath2D(camera, pipeline);

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            renderingPath.render().toMainScreen();

            window.swapBuffers();
            window.pollEvents();
        }

        renderingPath.delete();
        window.destroy();
    }

    private static Model2D buildModel2D() {
        final float s = 0.7f;
        final Vertex2D[] vertices = {
                R2D.vertex(Vector2.of(-s, -s), Vector3.of(+0.0f, +0.0f, +0.5f)),
                R2D.vertex(Vector2.of(-s, +s), Vector3.of(+0.0f, +1.0f, +0.5f)),
                R2D.vertex(Vector2.of(+s, +s), Vector3.of(+1.0f, +1.0f, +0.5f)),
                R2D.vertex(Vector2.of(+s, -s), Vector3.of(+1.0f, +0.0f, +0.5f))
        };
        final int[] indices = {0, 1, 2, 0, 2, 3};

        final Mesh2D mesh = R2D.mesh(vertices, indices);
        return new Model2D(mesh);
    }
}
