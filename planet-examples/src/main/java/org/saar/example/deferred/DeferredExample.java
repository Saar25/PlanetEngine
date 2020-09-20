package org.saar.example.deferred;

import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjRenderNode;
import org.saar.core.common.r3d.*;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.light.LightRenderPass;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.Scale;

public class DeferredExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final ICamera camera = new Camera(projection);

        camera.getTransform().setPosition(Position.of(0, 0, 200));
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        MyNode node;
        ObjRenderNode renderNode = null;
        Texture2D texture = null;
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/cottage/cottage.obj");
            texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            node = new MyNode(texture);

            renderNode = new ObjRenderNode(mesh, node);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final ObjDeferredRenderer renderer = new ObjDeferredRenderer(camera, new ObjRenderNode[]{renderNode});

        final Node3D cube = new NodeBase3D();
        cube.getTransform().setPosition(Position.of(0, 50, 0));
        cube.getTransform().setScale(Scale.of(10, 10, 10));
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Node3D[]{cube});
        final RenderNode3D cubeRenderNode = new RenderNode3D(cubeMesh);
        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(camera, new RenderNode3D[]{cubeRenderNode});

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(screenPrototype);
        deferredRenderer.addRenderer(renderer3D);
        deferredRenderer.addRenderer(renderer);
        deferredRenderer.addRenderPass(new LightRenderPass(camera));

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);

        long current = System.currentTimeMillis();
        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {

            deferredRenderer.render();

            window.update(true);
            window.pollEvents();

            final long delta = System.currentTimeMillis() - current;
            ExamplesUtils.move(camera, keyboard, delta);

            System.out.print("\rFps: " +
                    1000f / (-current + (current = System.currentTimeMillis()))
            );
        }

        renderer.delete();
        deferredRenderer.delete();
        window.destroy();
    }

}
