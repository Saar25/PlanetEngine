package org.saar.example.renderer3d;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.r3d.Mesh3D;
import org.saar.core.common.r3d.RenderNode3D;
import org.saar.core.common.r3d.Renderer3D;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.Rotation;
import org.saar.maths.utils.Quaternion;

public class Renderer3DExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final int CUBES = 1_000_000;
    private static final int AREA = 1000;
    private static final int BATCHES = 10;

    private static ColourAttachment colorAttachment;
    private static DepthAttachment depthAttachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();

        colorAttachment = ColourAttachment.withRenderBuffer(0, FormatType.RGBA8);
        depthAttachment = DepthAttachment.withRenderBuffer(DepthFormatType.COMPONENT24);

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 5000);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 0, -1000));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final Renderer3D renderer = new Renderer3D(camera, renderNode3D());

        final Keyboard keyboard = window.getKeyboard();
        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

//            renderNode.update();
            ExamplesUtils.move(camera, keyboard);
            renderer.render();

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

    private static RenderNode3D[] renderNode3D() {
        final int cubesPerBatch = CUBES / BATCHES;
        final RenderNode3D[] batches = new RenderNode3D[BATCHES];
        final MyNode[] nodes = new MyNode[cubesPerBatch];
        for (int i = 0; i < BATCHES; i++) {
            for (int j = 0; j < cubesPerBatch; j++) {
                final MyNode newNode = new MyNode();
                final float x = (float) (Math.random() * AREA - AREA / 2);
                final float y = (float) (Math.random() * AREA - AREA / 2);
                final float z = (float) (Math.random() * AREA - AREA / 2);
                newNode.getTransform().setPosition(Position.of(x, y, z));
                newNode.getTransform().setRotation(Rotation.fromQuaternion(Quaternion.of((float) Math.random(),
                        (float) Math.random(), (float) Math.random(), (float) Math.random()).normalize()));
                nodes[j] = newNode;
            }
            final Mesh3D mesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, nodes);
            batches[i] = new RenderNode3D(mesh);
        }
        return batches;
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
