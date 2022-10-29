package org.saar.example.terrain;

import org.joml.SimplexNoise;
import org.joml.Vector2i;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.components.*;
import org.saar.core.common.obj.Obj;
import org.saar.core.common.obj.ObjModel;
import org.saar.core.common.obj.ObjNode;
import org.saar.core.common.obj.ObjNodeBatch;
import org.saar.core.common.r3d.Instance3D;
import org.saar.core.common.r3d.Model3D;
import org.saar.core.common.r3d.Node3D;
import org.saar.core.common.r3d.R3D;
import org.saar.core.common.terrain.colour.ColourGenerator;
import org.saar.core.common.terrain.colour.NormalColour;
import org.saar.core.common.terrain.colour.NormalColourGenerator;
import org.saar.core.common.terrain.components.TerrainGravityComponent;
import org.saar.core.common.terrain.components.TerrainJumpingComponent;
import org.saar.core.common.terrain.components.TerrainWalkingComponent;
import org.saar.core.common.terrain.height.HeightGenerator;
import org.saar.core.common.terrain.height.NoiseHeightGenerator;
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainFactory;
import org.saar.core.common.terrain.lowpoly.LowPolyWorld;
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator;
import org.saar.core.fog.Fog;
import org.saar.core.fog.FogDistance;
import org.saar.core.light.DirectionalLight;
import org.saar.core.mesh.Mesh;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.postprocessing.processors.SkyboxPostProcessor;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPipeline;
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass;
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass;
import org.saar.core.renderer.deferred.passes.SsaoRenderPass;
import org.saar.core.renderer.forward.passes.FogRenderPass;
import org.saar.core.renderer.shadow.ShadowsQuality;
import org.saar.core.renderer.shadow.ShadowsRenderNode;
import org.saar.core.renderer.shadow.ShadowsRenderNodeGroup;
import org.saar.core.renderer.shadow.ShadowsRenderingPath;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.texture.CubeMapTexture;
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.maths.noise.LayeredNoise2f;
import org.saar.maths.noise.MultipliedNoise2f;
import org.saar.maths.noise.SpreadNoise2f;
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

        final LowPolyWorld world = buildWorld();
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                world.createTerrain(new Vector2i(x, z));
            }
        }

        final NodeComponentGroup playerComponents = new NodeComponentGroup(
                new VelocityComponent(),
                new AccelerationComponent(),
                new TerrainGravityComponent(world),
                new TerrainJumpingComponent(world, keyboard, 5),
                new TerrainWalkingComponent(world, keyboard, 5, 5)
        );
        final Model3D playerModel = buildCubeModel();
        final Node3D player = new Node3D(playerModel, playerComponents);

        final NodeComponentGroup components = new NodeComponentGroup(
                new MouseDragRotationComponent(mouse, -.3f),
                new ThirdPersonViewComponent(player.getModel().getTransform(), 5f)
        );
        final Camera camera = new Camera(projection, components);

        player.getComponents().add(player, new BackFaceComponent(camera.getTransform(), .1f));

        final Mesh mesh = Obj.mesh("/assets/tree/tree.model.obj");
        final Texture2D texture = Texture2D.of("/assets/tree/tree.diffuse.png");
        final ObjNodeBatch treesNodeBatch = new ObjNodeBatch(IntStream.range(0, 1000).mapToObj(i -> {
            final ObjModel treeModel = new ObjModel(mesh, texture);
            final ObjNode tree = new ObjNode(treeModel);
            final float x = (float) (Math.random() * 200 - 100);
            final float z = (float) (Math.random() * 200 - 100);
            treeModel.getTransform().getPosition().set(x, world.getHeight(x, z) + 2, z);
            return tree;
        }).toArray(ObjNode[]::new));

        final Model3D cubeModel = buildCubeModel();
        final Node3D cube = new Node3D(cubeModel);

        final Model3D cubeModel2 = buildCubeModel();
        cubeModel2.getTransform().getPosition().addX(-5);
        cubeModel2.getTransform().getPosition().addY(5);
        final Node3D cube2 = new Node3D(cubeModel2);

        final DirectionalLight light = buildDirectionalLight();

        final ShadowsRenderNode shadowsRenderNode = new ShadowsRenderNodeGroup(cube, cube2, player, treesNodeBatch, world);

        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.MEDIUM, shadowProjection, light, shadowsRenderNode);
        final ReadOnlyTexture2D shadowMap = shadowsRenderingPath.render().getBuffers().getDepth();

        final DeferredRenderNode renderNode = new DeferredRenderNodeGroup(cube, cube2, player, treesNodeBatch, world);
        final RenderingPath<?> renderingPath = buildRenderingPath(camera,
                renderNode, light, shadowsRenderingPath.getCamera(), shadowMap);

        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            renderNode.update();
            camera.update();

            renderingPath.render().toMainScreen();

            window.swapBuffers();
            window.pollEvents();
        }

        camera.delete();
        renderingPath.delete();
        window.destroy();
    }

    private static LowPolyWorld buildWorld() {
        final HeightGenerator heightGenerator = new NoiseHeightGenerator(
                new MultipliedNoise2f(50, new SpreadNoise2f(5,
                        new LayeredNoise2f(SimplexNoise::noise, 5)))
        );

        final ColourGenerator colourGenerator = new NormalColourGenerator(Vector3.upward(),
                new NormalColour(0.2f, Vector3.of(.41f, .41f, .41f)),
                new NormalColour(0.3f, Vector3.of(.07f, .52f, .06f)));

        final LowPolyTerrainFactory terrainFactory = new LowPolyTerrainFactory(
                new DiamondMeshGenerator(64), heightGenerator,
                colourGenerator, Vector2.of(32, 32)
        );

        return new LowPolyWorld(terrainFactory);
    }

    private static Model3D buildCubeModel() {
        final Instance3D cubeInstance = R3D.instance();
        final Mesh cubeMesh = R3D.mesh(new Instance3D[]{cubeInstance},
                ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices);
        return new Model3D(cubeMesh);
    }

    private static DirectionalLight buildDirectionalLight() {
        final DirectionalLight light = new DirectionalLight();
        light.getDirection().set(-1, -.6, -1);
        light.getColour().set(1, 1, 1);
        return light;
    }

    private static DeferredRenderingPath buildRenderingPath(
            ICamera camera, DeferredRenderNode renderNode, DirectionalLight light,
            ICamera shadowCamera, ReadOnlyTexture2D shadowMap) throws IOException {
        final CubeMapTexture cubeMap = createCubeMap();
        final Fog fog = new Fog(Vector3.of(0), 700, 1000);

        final DeferredRenderingPipeline renderPassesPipeline = new DeferredRenderingPipeline(
                new DeferredGeometryPass(renderNode),
                new ShadowsRenderPass(shadowCamera, shadowMap, light),
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
