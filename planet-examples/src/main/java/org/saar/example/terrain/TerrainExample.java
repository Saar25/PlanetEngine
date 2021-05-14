package org.saar.example.terrain;

import org.joml.SimplexNoise;
import org.saar.core.behavior.BehaviorGroup;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.behaviors.KeyboardMovementBehavior;
import org.saar.core.common.behaviors.KeyboardMovementScrollVelocityBehavior;
import org.saar.core.common.behaviors.MouseRotationBehavior;
import org.saar.core.common.r3d.*;
import org.saar.core.common.terrain.colour.NormalColour;
import org.saar.core.common.terrain.colour.NormalColourGenerator;
import org.saar.core.common.terrain.height.NoiseHeightGenerator;
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainConfiguration;
import org.saar.core.common.terrain.lowpoly.LowPolyWorld;
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator;
import org.saar.core.fog.Fog;
import org.saar.core.fog.FogDistance;
import org.saar.core.light.DirectionalLight;
import org.saar.core.postprocessing.PostProcessingBuffers;
import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.FogPostProcessor;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderPassesPipeline;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.renderpass.shadow.ShadowsRenderPass;
import org.saar.core.renderer.shadow.ShadowsQuality;
import org.saar.core.renderer.shadow.ShadowsRenderNode;
import org.saar.core.renderer.shadow.ShadowsRenderNodeGroup;
import org.saar.core.renderer.shadow.ShadowsRenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

public class TerrainExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        GlUtils.setClearColour(.2f, .2f, .2f);

        final Keyboard keyboard = window.getKeyboard();
        final Mouse mouse = window.getMouse();

        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);

        final KeyboardMovementBehavior cameraMovementBehavior =
                new KeyboardMovementBehavior(keyboard, 50f, 50f, 50f);
        final BehaviorGroup behaviors = new BehaviorGroup(cameraMovementBehavior,
                new KeyboardMovementScrollVelocityBehavior(mouse),
                new MouseRotationBehavior(mouse, -.3f));

        final Camera camera = new Camera(projection, behaviors);
        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));

        final LowPolyWorld world = new LowPolyWorld(new LowPolyTerrainConfiguration(
                new DiamondMeshGenerator(64),
                new NoiseHeightGenerator(SimplexNoise::noise),
                new NormalColourGenerator(Vector3.upward(),
                        new NormalColour(0.5f, Vector3.of(.41f, .41f, .41f)),
                        new NormalColour(1.0f, Vector3.of(.07f, .52f, .06f))),
                Vector2.of(256, 256), 100
        ));

        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                world.createTerrain(Vector2.of(x, z));
            }
        }

        final Model3D cubeModel = buildCubeModel();
        final Node3D cube = new Node3D(cubeModel);

        final DirectionalLight light = buildDirectionalLight();

        final ShadowsRenderNode shadowsRenderNode = new ShadowsRenderNodeGroup(cube);
        final ShadowsRenderingPath shadowsRenderingPath = buildShadowsRenderingPath(shadowsRenderNode, light);

        final DeferredRenderNodeGroup renderNode = new DeferredRenderNodeGroup(cube, world);
        final DeferredRenderingPath renderingPath = buildRenderingPath(camera, renderNode, shadowsRenderingPath, light);

        final Fog fog = new Fog(Vector3.of(.0f, .7f, .8f), 400, 450);

        final PostProcessingPipeline postProcessing = new PostProcessingPipeline(
                new ContrastPostProcessor(1.3f),
                new FogPostProcessor(fog, true, FogDistance.XZ),
                new FxaaPostProcessor()
        );

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            final PostProcessingBuffers buffers =
                    renderingPath.render().asPostProcessingInput();
            postProcessing.process(camera, buffers).toMainScreen();

            window.update(true);
            window.pollEvents();

            final double delta = fps.delta() * 1000;

            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", cameraMovementBehavior.getVelocity().x()) +
                    ", Fps: " + String.format("%.2f", fps.fps()) +
                    ", Delta: " + delta);
            fps.update();
        }

        camera.delete();
        shadowsRenderingPath.delete();
        renderingPath.delete();
        window.destroy();
    }

    private static Model3D buildCubeModel() {
        final Instance3D cubeInstance = R3D.instance();
        cubeInstance.getTransform().getScale().set(10, 10, 10);
        cubeInstance.getTransform().getPosition().set(0, 0, 50);
        final Mesh3D cubeMesh = Mesh3D.load(ExamplesUtils.cubeVertices,
                ExamplesUtils.cubeIndices, new Instance3D[]{cubeInstance});
        return new Model3D(cubeMesh);
    }

    private static DirectionalLight buildDirectionalLight() {
        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);
        return light;
    }

    private static ShadowsRenderingPath buildShadowsRenderingPath(ShadowsRenderNode renderNode, DirectionalLight light) {
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        return new ShadowsRenderingPath(ShadowsQuality.HIGH,
                shadowProjection, light, renderNode);
    }

    private static DeferredRenderingPath buildRenderingPath(ICamera camera, DeferredRenderNode renderNode,
                                                            ShadowsRenderingPath shadowsRenderingPath, DirectionalLight light) {
        final ReadOnlyTexture shadowMap = shadowsRenderingPath.render().toTexture();

        final DeferredRenderPassesPipeline renderPassesPipeline = new DeferredRenderPassesPipeline(
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light)
        );

        return new DeferredRenderingPath(camera, renderNode, renderPassesPipeline);
    }

}
