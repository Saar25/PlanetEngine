package org.saar.example.renderer3d;

import org.joml.Vector3fc;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.renderer.r3d.RenderNode3D;
import org.saar.core.renderer.r3d.Renderer3D;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.IFbo;
import org.saar.lwjgl.opengl.fbos.MultisampledFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.attachment.RenderBufferAttachmentMS;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, false);
        window.init();


        final Projection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 3000);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 3000, 0));
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
                final IFbo temp = fbo;
                fbo = createFbo(window.getWidth(), window.getHeight());
                temp.delete();
            }

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        renderer.delete();
    }

    private static void move(ICamera camera, Keyboard keyboard) {
        Vector3fc toMove = Vector3.ZERO;
        if (keyboard.isKeyPressed('W')) {
            toMove = Vector3.of(0, 0, -1);
        }
        if (keyboard.isKeyPressed('A')) {
            toMove = Vector3.of(-1, 0, 0);
        }
        if (keyboard.isKeyPressed('S')) {
            toMove = Vector3.of(0, 0, 1);
        }
        if (keyboard.isKeyPressed('D')) {
            toMove = Vector3.of(1, 0, 0);
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            toMove = Vector3.of(0, -10, 0);
        }
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            toMove = Vector3.of(0, 10, 0);
        }
        camera.getTransform().getPosition().add(toMove);
    }

    private static RenderNode3D renderNode3D() {
        final ModelIndices indices = new ModelIndices(Renderer3DExample.indices);
        final ModelVertices<MyVertex> vertices = new ModelVertices<>(flatData);

        final MyMesh model = new MyMesh(vertices, indices);
        final List<MyNode> nodes = new ArrayList<>();
        final int max = 1000000;
        final float sqrt = (float) Math.sqrt(max);
        for (int i = 0; i < max; i++) {
            final MyNode newNode = new MyNode();
            final float x = (i / sqrt) - (max / sqrt / 2);
            final float z = (i % sqrt) - sqrt / 2;
            final float y = (x * x + z * z) * 0.005f;
            newNode.getTransform().setPosition(Position.of(x * 1.1f, y, z * 1.1f));
            nodes.add(newNode);
        }
        return new RenderNode3D(model, nodes);
    }

    private static MultisampledFbo createFbo(int width, int height) {
        final MultisampledFbo fbo = new MultisampledFbo(width, height);
        fbo.addAttachment(new RenderBufferAttachmentMS(AttachmentType.COLOUR,
                0, RenderBuffer.create(), FormatType.BGRA, 16));
        fbo.addAttachment(new RenderBufferAttachmentMS(AttachmentType.DEPTH,
                0, RenderBuffer.create(), FormatType.DEPTH_COMPONENT, 16));
        return fbo;
    }

}
