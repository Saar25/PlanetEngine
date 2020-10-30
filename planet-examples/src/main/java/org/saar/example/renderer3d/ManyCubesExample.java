package org.saar.example.renderer3d;

import org.saar.core.camera.Camera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.r3d.Mesh3D;
import org.saar.core.common.r3d.Model3D;
import org.saar.core.common.r3d.Renderer3D;
import org.saar.core.renderer.RenderContextBase;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class ManyCubesExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final int CUBES = 50_000;
    private static final float SPACE = 2f;
    private static final int ROWS = 10;
    private static final int COLS = 10;

    private static ColourAttachment colorAttachment;
    private static DepthAttachment depthAttachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();

        colorAttachment = ColourAttachment.withRenderBuffer(0, InternalFormat.RGBA8);
        depthAttachment = DepthAttachment.withRenderBuffer(DepthFormatType.COMPONENT24);

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 5000);
        final Camera camera = new Camera(projection);

        final Model3D model = model();
        final Renderer3D renderer = new Renderer3D(model);

        final Keyboard keyboard = window.getKeyboard();
        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            ExamplesUtils.move(camera, keyboard);

            final int size = (int) Math.ceil(Math.pow(CUBES, 1 / 3.0) * SPACE);

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    model.getTransform().getPosition().set(
                            i * size - size / 2f, 0, j * size - size / 2f);
                    renderer.render(new RenderContextBase(camera));
                }
            }

            window.pollEvents();
            if (window.isResized()) {
                projection.setWidth(window.getWidth());
                projection.setHeight(window.getHeight());
            }

            window.update(true);

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        renderer.delete();
        colorAttachment.delete();
        depthAttachment.delete();
        window.destroy();
    }

    private static Model3D model() {
        final int size = (int) Math.pow(CUBES, 1 / 3f);
        final MyInstance[] nodes = new MyInstance[CUBES];
        for (int i = 0; i < CUBES; i++) {
            final int a = i / (size * size);
            final int b = (i / size) % size;
            final int c = i % size;
            final MyInstance newNode = new MyInstance();
            newNode.getTransform().getPosition().set(
                    a * SPACE, b * SPACE, c * SPACE);
            nodes[i] = newNode;
        }
        final Mesh3D mesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, nodes);
        return new Model3D(mesh);
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height, 16);
        fbo.addAttachment(depthAttachment);
        fbo.addAttachment(colorAttachment);
        fbo.setReadAttachment(colorAttachment);
        fbo.setDrawAttachments(colorAttachment);
        fbo.ensureStatus();
        return fbo;
    }

}
