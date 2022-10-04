package org.saar.example.terrain;

import org.joml.SimplexNoise;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.KeyboardMovementComponent;
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent;
import org.saar.core.common.components.MouseDragRotationComponent;
import org.saar.core.common.obj.Obj;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.Instance3D;
import org.saar.core.common.r3d.Model3D;
import org.saar.core.common.r3d.Node3D;
import org.saar.core.common.r3d.R3D;
import org.saar.core.common.terrain.colour.NormalColour;
import org.saar.core.common.terrain.colour.NormalColourGenerator;
import org.saar.core.common.terrain.height.NoiseHeightGenerator;
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainConfiguration;
import org.saar.core.common.terrain.lowpoly.LowPolyWorld;
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator;
import org.saar.core.fog.Fog;
import org.saar.core.fog.FogDistance;
import org.saar.core.light.DirectionalLight;
import org.saar.core.mesh.Mesh;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.postprocessing.processors.SkyboxPostProcessor;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPipeline;
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass;
import org.saar.core.renderer.deferred.passes.LightRenderPass;
import org.saar.core.renderer.deferred.passes.SsaoRenderPass;
import org.saar.core.renderer.forward.passes.FogRenderPass;
import org.saar.core.util.Fps;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.texture.CubeMapTexture;
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.maths.noise.LayeredNoise2f;
import org.saar.maths.noise.MultipliedNoise2f;
import org.saar.maths.noise.SpreadNoise2f;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

import java.io.IOException;
import java.util.stream.IntStream;

public class ForestExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(.0f, .7f, .8f);

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

        final NoiseHeightGenerator heightGenerator = new NoiseHeightGenerator(
                new MultipliedNoise2f(200,
                        new SpreadNoise2f(50,
                                new LayeredNoise2f(SimplexNoise::noise, 5)))
        );
        final LowPolyWorld world = new LowPolyWorld(new LowPolyTerrainConfiguration(
                new DiamondMeshGenerator(64), heightGenerator,
                new NormalColourGenerator(Vector3.upward(),
                        new NormalColour(0.5f, Vector3.of(.41f, .41f, .41f)),
                        new NormalColour(1.0f, Vector3.of(.07f, .52f, .06f))),
                Vector2.of(256, 256)
        ));

        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                world.createTerrain(Vector2.of(x, z));
            }
        }

        final Mesh mesh = Obj.mesh("/assets/tree/tree.model.obj");
        final Texture2D texture = Texture2D.of("/assets/tree/tree.diffuse.png");
        final ObjNodeBatch objNodeBatch = new ObjNodeBatch(IntStream.range(0, 1000).mapToObj(i -> {
            final ObjModel treeModel = new ObjModel(mesh, texture);
            final ObjNode tree = new ObjNode(treeModel);
            treeModel.getTransform().getScale().set(10);
            final float x = (float) (Math.random() * 2000 - 1000);
            final float z = (float) (Math.random() * 2000 - 1000);
            treeModel.getTransform().getPosition().set(x, heightGenerator.generateHeight(x, z) + 18, z);
            return tree;
        }).toArray(ObjNode[]::new));

        final Model3D cubeModel = buildCubeModel();
        final Node3D cube = new Node3D(cubeModel);

        final Model3D cubeModel2 = buildCubeModel();
        cubeModel2.getTransform().getPosition().addX(-5);
        cubeModel2.getTransform().getPosition().addY(5);
        final Node3D cube2 = new Node3D(cubeModel2);

        final DirectionalLight light = buildDirectionalLight();

        final CubeMapTexture cubeMap = createCubeMap();
        final DeferredRenderNodeGroup renderNode = new DeferredRenderNodeGroup(cube, cube2, objNodeBatch, world);
        final DeferredRenderingPath renderingPath = buildRenderingPath(camera, renderNode, light, cubeMap);

        final Fps fps = new Fps();
        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            camera.update();

            renderingPath.render().toMainScreen();

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
        renderingPath.delete();
        window.destroy();
    }

    private static Model3D buildCubeModel() {
        final Instance3D cubeInstance = R3D.instance();
        cubeInstance.getTransform().getScale().set(10, 10, 10);
        cubeInstance.getTransform().getPosition().set(0, 0, 50);
        final Mesh cubeMesh = R3D.mesh(new Instance3D[]{cubeInstance},
                ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices);
        return new Model3D(cubeMesh);
    }

    private static DirectionalLight buildDirectionalLight() {
        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -1, -1);
        light.getColour().set(1, 1, 1);
        return light;
    }

    private static DeferredRenderingPath buildRenderingPath(ICamera camera, DeferredRenderNode renderNode,
                                                            DirectionalLight light, CubeMapTexture cubeMap) {
        final Fog fog = new Fog(Vector3.of(0), 700, 1000);

        final DeferredRenderingPipeline renderPassesPipeline = new DeferredRenderingPipeline(
                new DeferredGeometryPass(renderNode),
                new LightRenderPass(light),
                new SsaoRenderPass(),
                new FogRenderPass(fog, FogDistance.XZ),
                new SkyboxPostProcessor(cubeMap),
                new FxaaPostProcessor()
        );

        return new DeferredRenderingPath(camera, renderPassesPipeline);
    }

    private static CubeMapTexture createCubeMap() throws IOException {
        return new CubeMapTextureBuilder()
                .positiveX("/assets/skybox/right.jpg")
                .negativeX("/assets/skybox/left.jpg")
                .positiveY("/assets/skybox/top.jpg")
                .negativeY("/assets/skybox/bottom.jpg")
                .positiveZ("/assets/skybox/front.jpg")
                .negativeZ("/assets/skybox/back.jpg")
                .create();
    }
}
