package org.saar.example.shadow;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.PerspectiveProjection;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjRenderNode;
import org.saar.core.common.r3d.*;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

public class ShadowExample {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        final Window window = new Window("Lwjgl", WIDTH, HEIGHT, true);
        window.init();

        final PerspectiveProjection projection = new PerspectiveProjection(70f, WIDTH, HEIGHT, 1, 1000);
        final Camera camera = new Camera(projection);

        camera.getTransform().getPosition().set(0, 0, 200);
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
        cube.getTransform().getScale().set(10, 10, 10);
        cube.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices, new Node3D[]{cube});
        final RenderNode3D cubeRenderNode = new RenderNode3D(cubeMesh);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(camera, new RenderNode3D[]{cubeRenderNode});

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final Keyboard keyboard = window.getKeyboard();

        final OrthographicProjection shadowProjection = new OrthographicProjection(-100, 100, -100, 100, -100, 100);
        final Camera shadowsCamera = new Camera(shadowProjection);
        shadowsCamera.getTransform().getPosition().set(Vector3.of(1).normalize());
        shadowsCamera.getTransform().lookAt(Position.of(0, 0, 0));
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(4096);
        shadowsRenderingPath.addRenderer(new DeferredRenderer3D(shadowsCamera, new RenderNode3D[]{cubeRenderNode}));
        shadowsRenderingPath.addRenderer(new ObjDeferredRenderer(shadowsCamera, new ObjRenderNode[]{renderNode}));

        final DeferredRenderingPath deferredRenderer = new DeferredRenderingPath(screenPrototype);
        deferredRenderer.addRenderer(renderer);
        deferredRenderer.addRenderer(renderer3D);

        deferredRenderer.addRenderPass(new ShadowsRenderPass(
                camera, shadowsCamera, shadowsRenderingPath.getShadowMap()));

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);

        long current = System.currentTimeMillis();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {

            shadowsRenderingPath.render();
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
        shadowsRenderingPath.delete();
        deferredRenderer.delete();
        window.destroy();
    }

}
