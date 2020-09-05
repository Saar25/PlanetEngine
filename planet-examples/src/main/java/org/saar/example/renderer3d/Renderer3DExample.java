package org.saar.example.renderer3d;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.renderer.r3d.RenderNode3D;
import org.saar.core.renderer.r3d.Renderer3D;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.colour.ColourAttachmentMS;
import org.saar.lwjgl.opengl.fbos.attachment.depth.DepthAttachmentMS;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.Rotation;
import org.saar.maths.utils.Quaternion;
import org.saar.maths.utils.Vector3;

public class Renderer3DExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final int CUBES = 1_000_000;
    private static final int AREA = 1000;
    private static final int BATCHES = 10;

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

    private static ColourAttachmentMS colorAttachment;
    private static DepthAttachmentMS depthAttachment;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();

        colorAttachment = new ColourAttachmentMS(0, AttachmentRenderBuffer.create(FormatType.BGRA), 16);
        depthAttachment = new DepthAttachmentMS(AttachmentRenderBuffer.create(DepthFormatType.COMPONENT24), 16);

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
            move(camera, keyboard);
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

    private static void move(ICamera camera, Keyboard keyboard) {
        final Vector3f toMove = Vector3.zero();
        final Vector3f toRotate = Vector3.zero();
        if (keyboard.isKeyPressed('W')) {
            toMove.add(0, 0, 1);
        }
        if (keyboard.isKeyPressed('A')) {
            toMove.add(1, 0, 0);
        }
        if (keyboard.isKeyPressed('S')) {
            toMove.add(0, 0, -1);
        }
        if (keyboard.isKeyPressed('D')) {
            toMove.add(-1, 0, 0);
        }
        if (keyboard.isKeyPressed('Q')) {
            toRotate.add(0, .5f, 0);
        }
        if (keyboard.isKeyPressed('E')) {
            toRotate.add(0, -.5f, 0);
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            toMove.add(0, -1, 0);
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            toMove.add(0, 1, 0);
        }
        camera.getTransform().getPosition().add(toMove.rotate(camera.getTransform().getRotation().getValue()).mul(-1));
        camera.getTransform().addRotation(Angle.degrees(toRotate.x), Angle.degrees(toRotate.y), Angle.degrees(toRotate.z));
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
            batches[i] = new RenderNode3D(flatData, indices, nodes);
        }
        return batches;
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
