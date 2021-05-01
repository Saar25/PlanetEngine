package org.saar.example.terrain;

import org.joml.SimplexNoise;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.obj.ObjDeferredRenderer;
import org.saar.core.common.obj.ObjMesh;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.r3d.*;
import org.saar.core.common.terrain.height.NoiseHeightGenerator;
import org.saar.core.common.terrain.lowpoly.LowPolyTerrain;
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainConfiguration;
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator;
import org.saar.core.common.terrain.mesh.SquareMeshGenerator;
import org.saar.core.common.terrain.mesh.TriangleMeshGenerator;
import org.saar.core.light.DirectionalLight;
import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.RenderPassesPipeline;
import org.saar.core.renderer.deferred.shadow.ShadowsQuality;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderPass;
import org.saar.core.renderer.deferred.shadow.ShadowsRenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.example.MyScreenPrototype;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.textures.ColourTexture;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.Angle;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;

import java.util.Objects;

public class TerrainExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    private static float scrollSpeed = 50f;

    public static void main(String[] args) {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, false);

        GlUtils.setClearColour(.15f, .15f, .15f);

        final Camera camera = buildCamera();

        final ObjDeferredRenderer objRenderer = new ObjDeferredRenderer(
                buildDragonModel(),
                buildStallModel());

        final LowPolyTerrain lowPolyTerrain = new LowPolyTerrain(new LowPolyTerrainConfiguration(
                new DiamondMeshGenerator(64),
                new NoiseHeightGenerator(SimplexNoise::noise),
                (x, y, z) -> Vector3.of(.3f + (float) Math.random() * .2f, .8f + (float) Math.random() * .2f, 0)
        ));
        lowPolyTerrain.getModel().getTransform().getScale().scale(256, 10, 256);
        lowPolyTerrain.getModel().getTransform().getPosition().set(0, -10, 0);

        final DeferredRenderer3D renderer3D = new DeferredRenderer3D(
                lowPolyTerrain.getModel(),
                buildCubeModel());

        final DirectionalLight light = buildDirectionalLight();

        final RenderersGroup renderersGroup = new RenderersGroup(objRenderer, renderer3D);
        final ShadowsRenderingPath shadowsRenderingPath = buildShadowsRenderingPath(renderersGroup, light);

        final RenderingPath renderingPath = buildRenderingPath(camera, renderersGroup, shadowsRenderingPath, light);

        final PostProcessingPipeline postProcessing = new PostProcessingPipeline(
                new ContrastPostProcessor(1.3f),
                new FxaaPostProcessor()
        );

        final Mouse mouse = window.getMouse();
        ExamplesUtils.addRotationListener(camera, mouse);
        mouse.addScrollListener(e -> {
            scrollSpeed += e.getOffset();
            scrollSpeed = Math.max(scrollSpeed, 1);
        });

        final Fps fps = new Fps();

        final Keyboard keyboard = window.getKeyboard();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {

            final ReadOnlyTexture texture =
                    renderingPath.render().toTexture();
            postProcessing.process(texture).toMainScreen();

            window.update(true);
            window.pollEvents();

            final double delta = fps.delta() * 1000;
            ExamplesUtils.move(camera, keyboard, (long) delta, scrollSpeed);

            System.out.print("\r --> " +
                    "Speed: " + String.format("%.2f", scrollSpeed) +
                    ", Fps: " + String.format("%.2f", fps.fps()) +
                    ", Delta: " + delta);
            fps.update();
        }

        shadowsRenderingPath.delete();
        renderingPath.delete();
        window.destroy();
    }

    private static Camera buildCamera() {
        final Projection projection = new ScreenPerspectiveProjection(
                MainScreen.getInstance(), 70f, 1, 1000);

        final Camera camera = new Camera(projection);
        camera.getTransform().getPosition().set(0, 0, 200);
        camera.getTransform().lookAt(Position.of(0, 0, 0));
        return camera;
    }

    private static ObjModel buildDragonModel() {
        final ObjModel dragonModel = Objects.requireNonNull(loadDragon());
        dragonModel.getTransform().getPosition().set(50, 0, 0);
        return dragonModel;
    }

    private static ObjModel buildStallModel() {
        final ObjModel stallModel = Objects.requireNonNull(loadStall());
        stallModel.getTransform().getPosition().set(-50, 0, 0);
        stallModel.getTransform().getRotation().rotate(Angle.degrees(0), Angle.degrees(180), Angle.degrees(0));
        return stallModel;
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

    private static ShadowsRenderingPath buildShadowsRenderingPath(RenderersGroup shadowsRenderersGroup, DirectionalLight light) {
        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        return new ShadowsRenderingPath(ShadowsQuality.HIGH,
                shadowProjection, light, shadowsRenderersGroup);
    }

    private static RenderingPath buildRenderingPath(ICamera camera, RenderersGroup renderersGroup,
                                                    ShadowsRenderingPath shadowsRenderingPath, DirectionalLight light) {
        final ReadOnlyTexture shadowMap = shadowsRenderingPath.render().toTexture();

        final MyScreenPrototype screenPrototype = new MyScreenPrototype();

        final RenderPassesPipeline renderPassesPipeline = new RenderPassesPipeline(
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, light)
        );

        return new DeferredRenderingPath(screenPrototype, camera, renderersGroup, renderPassesPipeline);
    }

    private static ObjModel loadStall() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/stall/stall.model.obj");
            final Texture2D texture = Texture2D.of("/assets/stall/stall.diffuse.png");
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ObjModel loadDragon() {
        try {
            final ObjMesh mesh = ObjMesh.load("/assets/dragon/dragon.model.obj");
            final ReadOnlyTexture texture = ColourTexture.of(255, 215, 0, 255);
            return new ObjModel(mesh, texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
