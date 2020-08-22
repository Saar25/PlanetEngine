package org.saar.example.renderer3d;

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
import org.saar.maths.Angle;
import org.saar.maths.objects.Transform;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

public class Renderer3DExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static MyVertex[] flatData = new MyVertex[]{ // xyz position, xyz normal,
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


        final ModelIndices indices = new ModelIndices(Renderer3DExample.indices);
        final ModelVertices<MyVertex> vertices = new ModelVertices<>(flatData);

        final MyNode node = new MyNode(vertices, indices, new Transform());
        node.getTransform().setPosition(Position.of(.5f, 0, 8.5f));
        final MyNode node2 = new MyNode(vertices, indices, new Transform());

        final Projection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 100);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 0, 15));
        camera.getTransform().lookAt(node.getTransform().getPosition());

        final RenderNode3D renderNode = new RenderNode3D(node, node, node2);
        final Renderer3D renderer = new Renderer3D(camera, renderNode);

        MultisampledFbo fbo = createFbo(WIDTH, HEIGHT);

        final Keyboard keyboard = window.getKeyboard();
        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('E')) {
            fbo.bind();
            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH);

            node.getTransform().addRotation(Angle.degrees(.1f), Angle.degrees(.1f), Angle.degrees(.1f));
            renderNode.update();
            renderer.render();

            fbo.blitToScreen();

            window.update(true);
            window.pollEvents();
            if (window.isResized()) {
                final IFbo temp = fbo;
                fbo = createFbo(window.getWidth(), window.getHeight());
                temp.delete();
            }

            System.out.print( "\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        renderer.delete();
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
