package org.saar.example.renderer3d;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.renderer.r3d.RenderNode3D;
import org.saar.core.renderer.r3d.Renderer3D;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

public class Renderer3DExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final MyVertex[] flatData = new MyVertex[]{ // xyz position, xyz normal,
            new MyVertex(Vector3.of(-0.5f, -0.5f, -0.5f), Vector3.of(+0, +0, -1).add(1, 1, 1).div(2)), // 0
            new MyVertex(Vector3.of(-0.5f, +0.5f, -0.5f), Vector3.of(+0, +1, +0).add(1, 1, 1).div(2)), // 1
            new MyVertex(Vector3.of(+0.5f, +0.5f, -0.5f), Vector3.of(+1, +0, +0).add(1, 1, 1).div(2)), // 2
            new MyVertex(Vector3.of(+0.5f, -0.5f, -0.5f), Vector3.of(+0, -1, +0).add(1, 1, 1).div(2)), // 3
            new MyVertex(Vector3.of(-0.5f, -0.5f, +0.5f), Vector3.of(-1, +0, +0).add(1, 1, 1).div(2)), // 4
            new MyVertex(Vector3.of(-0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0).add(1, 1, 1).div(2)), // 5
            new MyVertex(Vector3.of(+0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0).add(1, 1, 1).div(2)), // 6
            new MyVertex(Vector3.of(+0.5f, -0.5f, +0.5f), Vector3.of(+0, +0, +1).add(1, 1, 1).div(2)), // 7
    };

    private static final int[] indices = {
            0, 1, 2, 0, 2, 3, // back   , PV: 0
            4, 5, 1, 4, 1, 0, // left   , PV: 4
            7, 6, 5, 7, 5, 4, // front  , PV: 7
            2, 6, 7, 2, 7, 3, // right  , PV: 2
            1, 5, 6, 1, 6, 2, // top    , PV: 1
            3, 7, 4, 3, 4, 0, // bottom , PV: 3
    };

    private static RenderBufferAttachmentMS colorAttachment;
    private static RenderBufferAttachmentMS depthAttachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();

        colorAttachment = RenderBufferAttachmentMS.ofColour(0, FormatType.BGRA, 16);
        depthAttachment = RenderBufferAttachmentMS.ofDepth(FormatType.DEPTH_COMPONENT24, 16);

        final Projection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 500);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 300, 0));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final Renderer3D renderer = new Renderer3D(camera, renderNode3D());

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            fbo.bind();
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

//            renderNode.update();
            move(camera, keyboard);
            renderer.render();

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                fbo.delete();
                fbo = createFbo(window.getWidth(), window.getHeight());
            }

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        renderer.delete();
        fbo.delete();
        colorAttachment.delete();
        depthAttachment.delete();
    }

    private static void move(ICamera camera, Keyboard keyboard) {
        final Vector3f toMove = Vector3.zero();
        if (keyboard.isKeyPressed('W')) {
            toMove.add(Vector3.of(0, 0, -1));
        }
        if (keyboard.isKeyPressed('A')) {
            toMove.add(Vector3.of(-1, 0, 0));
        }
        if (keyboard.isKeyPressed('S')) {
            toMove.add(Vector3.of(0, 0, 1));
        }
        if (keyboard.isKeyPressed('D')) {
            toMove.add(Vector3.of(1, 0, 0));
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            toMove.add(Vector3.of(0, -10, 0));
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            toMove.add(Vector3.of(0, 10, 0));
        }
        camera.getTransform().getPosition().add(toMove);
    }

    private static RenderNode3D renderNode3D() {
        final int max = 100000;

        final MyNode[] nodes = new MyNode[max];
        final float sqrt = (float) Math.sqrt(max);
        for (int i = 0; i < max; i++) {
            final MyNode newNode = new MyNode();
            final float x = (i / sqrt) - (max / sqrt / 2);
            final float z = (i % sqrt) - sqrt / 2;
            final float y = (x * x + z * z) * 0.005f;
            newNode.getTransform().setPosition(Position.of(x * 1.1f, y, z * 1.1f));
            nodes[i] = newNode;
        }
        return new RenderNode3D(flatData, indices, nodes);
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        fbo.addAttachment(depthAttachment);
        fbo.addAttachment(colorAttachment);
        fbo.setReadAttachment(colorAttachment);
        fbo.setDrawAttachments(colorAttachment);
        fbo.ensureStatus();
        return fbo;
    }

}
