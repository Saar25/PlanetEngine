package org.saar.example.terrain;

import org.joml.SimplexNoise;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL20;
import org.saar.core.camera.Camera;
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
import org.saar.core.renderer.RenderContext;
import org.saar.core.renderer.RenderGraph;
import org.saar.core.renderer.RenderGraphNodeKt;
import org.saar.core.renderer.deferred.DeferredRenderNode;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredScreenPrototype;
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass;
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass;
import org.saar.core.renderer.deferred.passes.SsaoRenderPass;
import org.saar.core.renderer.forward.passes.FogRenderPass;
import org.saar.core.renderer.renderpass.RenderPassKt;
import org.saar.core.renderer.shadow.*;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.ScreenKt;
import org.saar.core.screen.Screens;
import org.saar.example.ExamplesUtils;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.fbo.Fbo;
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy;
import org.saar.lwjgl.opengl.texture.CubeMapTexture;
import org.saar.lwjgl.opengl.texture.CubeMapTextureBuilder;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.maths.noise.LayeredNoise2f;
import org.saar.maths.noise.MultipliedNoise2f;
import org.saar.maths.noise.SpreadNoise2f;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL43C.GL_DEBUG_OUTPUT;

public class ForestExample {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);
        GL20.glDisable(GL_DEBUG_OUTPUT);

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

        player.getComponents().add(new BackFaceComponent(camera.getTransform(), .1f));

        final Mesh mesh = Obj.mesh("/assets/tree/tree.model.obj");
        final Texture2D texture = Texture2D.of("/assets/tree/tree.diffuse.png");
        final ObjNodeBatch treesNodeBatch = new ObjNodeBatch(IntStream.range(0, 1000).<ObjNode>mapToObj(i -> {
            final ObjModel treeModel = new ObjModel(mesh, texture);
            final ObjNode tree = new ObjNode(treeModel);
            final float x = (float) (Math.random() * 200 - 100);
            final float z = (float) (Math.random() * 200 - 100);
            treeModel.getTransform().getPosition().set(x, world.getHeight(x, 0, z) + 2, z);
            return tree;
        }).toArray(ObjNode[]::new));

        final Model3D cubeModel = buildCubeModel();
        final Node3D cube = new Node3D(cubeModel);

        final Model3D cubeModel2 = buildCubeModel();
        cubeModel2.getTransform().getPosition().addX(-5);
        cubeModel2.getTransform().getPosition().addY(5);
        final Node3D cube2 = new Node3D(cubeModel2);

        final DirectionalLight light = buildDirectionalLight();

        final OrthographicProjection shadowProjection = new SimpleOrthographicProjection(
                -100, 100, -100, 100, -100, 100);
        final ShadowsScreenPrototype shadowsPrototype = new ShadowsScreenPrototype();
        final OffScreen shadowsScreen = Screens.INSTANCE.toScreen(
                shadowsPrototype,
                Fbo.create(ShadowsQuality.MEDIUM.getImageSize(), ShadowsQuality.MEDIUM.getImageSize()),
                SimpleAllocationStrategy.INSTANCE);
        final ShadowsCamera shadowsCamera = new ShadowsCamera(shadowProjection, light);

        final ReadOnlyTexture2D shadowMap = shadowsPrototype.getBuffers().getDepth();

        final RenderGraph shadowsRenderGraph = new RenderGraph(
                RenderGraphNodeKt.onto(
                        ShadowsRenderNodeKt.asShadowsRenderNode(
                                new ShadowsRenderNodeGroup(cube, cube2, treesNodeBatch, world, player)), shadowsScreen)
        );


        final DeferredRenderNode renderNode = new DeferredRenderNodeGroup(cube, cube2, player, treesNodeBatch, world);

        final CubeMapTexture cubeMap = createCubeMap();
        final Fog fog = new Fog(Vector3.of(0), 700, 1000);

        final DeferredScreenPrototype prototype1 = new DeferredScreenPrototype();
        final OffScreen screen1 = Screens.INSTANCE.toScreen(prototype1, Fbo.create(WIDTH, HEIGHT), SimpleAllocationStrategy.INSTANCE);

        final DeferredScreenPrototype prototype2 = new DeferredScreenPrototype();
        final OffScreen screen2 = Screens.INSTANCE.toScreen(prototype2, Fbo.create(WIDTH, HEIGHT), SimpleAllocationStrategy.INSTANCE);

        final RenderGraph renderGraph = new RenderGraph(
                RenderGraphNodeKt.onto(
                        RenderPassKt.asRenderNode(new DeferredGeometryPass(renderNode), prototype1.getBuffers()), screen1),
                RenderGraphNodeKt.onto(
                        RenderPassKt.asRenderNode(new ShadowsRenderPass(shadowsCamera, shadowMap, light), prototype1.getBuffers()), screen2),
                RenderGraphNodeKt.onto(
                        RenderPassKt.asRenderNode(new SsaoRenderPass(), prototype2.getBuffers()), screen1),
                RenderGraphNodeKt.onto(
                        RenderPassKt.asRenderNode(new FogRenderPass(fog, FogDistance.XZ), prototype1.getBuffers()), screen2),
                RenderGraphNodeKt.onto(
                        RenderPassKt.asRenderNode(new SkyboxPostProcessor(cubeMap), prototype2.getBuffers()), screen1),
                RenderGraphNodeKt.onto(
                        RenderPassKt.asRenderNode(new FxaaPostProcessor(), prototype1.getBuffers()), MainScreen.INSTANCE)
        );

        long last = System.currentTimeMillis();

        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            long delta = System.currentTimeMillis() - last;
            last = System.currentTimeMillis();
            System.out.print("\r --->" + delta);

            renderNode.update();
            camera.update();

            ScreenKt.clear(shadowsScreen, GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);
            shadowsRenderGraph.render(new RenderContext(shadowsCamera));

            ScreenKt.clear(screen1, GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);
            ScreenKt.clear(screen2, GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);
            ScreenKt.clear(MainScreen.INSTANCE, GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);
            renderGraph.render(new RenderContext(camera));

            window.swapBuffers();
            window.pollEvents();
        }

        camera.delete();
        screen1.delete();
        screen2.delete();
        renderGraph.delete();
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
