package org.saar.example.shadow;

import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent;
import org.saar.core.common.components.MouseDragRotationComponent;
import org.saar.core.common.obj.Obj;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.*;
import org.saar.core.common.renderpass.ShadowsRenderPass;
import org.saar.core.light.DirectionalLight;
import org.saar.core.mesh.Mesh;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.renderer.RenderContext;
import org.saar.core.renderer.RenderGraph;
import org.saar.core.renderer.RenderGraphNode;
import org.saar.core.renderer.RenderGraphNodeKt;
import org.saar.core.renderer.deferred.DeferredNodeRenderPass;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredScreenPrototype;
import org.saar.core.renderer.shadow.*;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.ScreenKt;
import org.saar.core.screen.Screens;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy;
import org.saar.lwjgl.opengl.texture.ColourTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.maths.transform.Position;

import java.util.Objects;

public class ShadowExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        final Keyboard keyboard = window.getKeyboard();
        final Mouse mouse = window.getMouse();

        final Projection projection = new ScreenPerspectiveProjection(70f, 1, 1000);

        final KeyboardMovementComponent cameraMovementComponent =
                new KeyboardMovementComponent(keyboard, 50f, 50f, 50f);
        final NodeComponentGroup components = new NodeComponentGroup(cameraMovementComponent,
                new KeyboardMovementScrollVelocityComponent(mouse),
                new MouseDragRotationComponent(mouse, -.3f));

        final Camera camera = new Camera(projection, components);

        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final NodeBatch3D nodeBatch3D = buildNodeBatch3D();

        final ObjNodeBatch objNodeBatch = buildObjNodeBatch();

        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);

        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsScreenPrototype shadowsPrototype = new ShadowsScreenPrototype();
        final OffScreen shadowsScreen = Screens.INSTANCE.toScreen(
                shadowsPrototype,
                Fbo.create(ShadowsQuality.MEDIUM.getImageSize(), ShadowsQuality.MEDIUM.getImageSize()),
                SimpleAllocationStrategy.INSTANCE);
        final ShadowsCamera shadowsCamera = new ShadowsCamera(shadowProjection, light);

        final ReadOnlyTexture2D shadowMap = shadowsPrototype.getDepthTexture();

        final RenderGraph shadowsRenderGraph = new RenderGraph(
                RenderGraphNodeKt.onto(new ShadowsNodeRenderPass(
                        camera, new ShadowsRenderNodeGroup(nodeBatch3D, objNodeBatch)
                ), shadowsScreen)
        );
        ScreenKt.clear(shadowsScreen, GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);
        shadowsRenderGraph.render(new RenderContext());

        final DeferredRenderNodeGroup renderNode = new DeferredRenderNodeGroup(nodeBatch3D, objNodeBatch);

        final DeferredScreenPrototype prototype = new DeferredScreenPrototype();
        final OffScreen screen = Screens.INSTANCE.toScreen(
                prototype,
                Fbo.create(window.getWidth(), window.getHeight()),
                SimpleAllocationStrategy.INSTANCE);

        final RenderGraph renderGraph = new RenderGraph(
                new RenderGraphNode(new DeferredNodeRenderPass(camera, renderNode), screen),
                new RenderGraphNode(
                        new ShadowsRenderPass(
                                prototype.getAlbedoTexture(),
                                prototype.getNormalSpecularTexture(),
                                prototype.getDepthTexture(),
                                shadowsCamera,
                                camera,
                                shadowMap,
                                light
                        ), MainScreen.INSTANCE)
        );

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            ScreenKt.clear(screen, GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);
            ScreenKt.clear(MainScreen.INSTANCE, GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);
            renderGraph.render(new RenderContext());

            window.swapBuffers();
            window.pollEvents();

            final double delta = fps.delta() * 1000;

            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", cameraMovementComponent.getVelocity().x()) +
                    ", Fps: " + String.format("%.2f", fps.fps()) +
                    ", Delta: " + delta);
            fps.update();
        }

        camera.delete();
        shadowsScreen.delete();
        shadowsRenderGraph.delete();
        screen.delete();
        renderGraph.delete();
        window.destroy();
    }

    private static NodeBatch3D buildNodeBatch3D() {
        final Instance3D cubeInstance = R3D.instance();
        cubeInstance.getTransform().getScale().set(10, 10, 10);
        cubeInstance.getTransform().getPosition().set(0, 0, 50);
        final Mesh cubeMesh = R3D.mesh(new Instance3D[]{cubeInstance},
                ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices);
        final Model3D cubeModel = new Model3D(cubeMesh);
        final Node3D cube = new Node3D(cubeModel);

        return new NodeBatch3D(cube);
    }

    private static ObjNodeBatch buildObjNodeBatch() {
        final ObjModel cottageModel = Objects.requireNonNull(loadCottage());
        final ObjNode cottage = new ObjNode(cottageModel);

        final ObjModel dragonModel = Objects.requireNonNull(loadDragon());
        dragonModel.getTransform().getPosition().set(50, 0, 0);
        final ObjNode dragon = new ObjNode(dragonModel);

        final ObjModel stallModel = Objects.requireNonNull(loadStall());
        stallModel.getTransform().getPosition().set(-50, 0, 0);
        stallModel.getTransform().getRotation().rotateDegrees(0f, 180f, 0f);
        final ObjNode stall = new ObjNode(stallModel);

        return new ObjNodeBatch(cottage, dragon, stall);
    }

    private static ObjModel loadCottage() {
        try {
            final Mesh mesh = Obj.mesh("/assets/cottage/cottage.obj");
            final Texture2D texture = Texture2D.of("/assets/cottage/cottage_diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadStall() {
        try {
            final Mesh mesh = Obj.mesh("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadDragon() {
        try {
            final Mesh mesh = Obj.mesh("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
