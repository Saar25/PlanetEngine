package org.saar.minecraft;

import org.joml.SimplexNoise;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.camera.projection.SimpleOrthographicProjection;
import org.saar.core.common.components.MouseRotationComponent;
import org.saar.core.common.components.VelocityComponent;
import org.saar.core.fog.Fog;
import org.saar.core.fog.FogDistance;
import org.saar.core.light.DirectionalLight;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup;
import org.saar.core.renderer.deferred.DeferredRenderingPath;
import org.saar.core.renderer.deferred.DeferredRenderingPipeline;
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass;
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass;
import org.saar.core.renderer.forward.passes.FogRenderPass;
import org.saar.core.renderer.p2d.GeometryPass2D;
import org.saar.core.renderer.shadow.ShadowsQuality;
import org.saar.core.renderer.shadow.ShadowsRenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.gui.UIBlock;
import org.saar.gui.UIDisplay;
import org.saar.gui.UIElement;
import org.saar.gui.UIText;
import org.saar.gui.style.Colour;
import org.saar.gui.style.Colours;
import org.saar.gui.style.alignment.AlignmentValues;
import org.saar.gui.style.coordinate.CoordinateValues;
import org.saar.gui.style.position.PositionValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.input.mouse.MouseCursor;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.constants.Face;
import org.saar.lwjgl.opengl.polygonmode.PolygonMode;
import org.saar.lwjgl.opengl.polygonmode.PolygonModeValue;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.lwjgl.opengl.texture.parameter.TextureAnisotropicFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter;
import org.saar.lwjgl.opengl.texture.values.MagFilterValue;
import org.saar.lwjgl.opengl.texture.values.MinFilterValue;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.chunk.ChunkRenderNode;
import org.saar.minecraft.chunk.ChunkRenderer;
import org.saar.minecraft.chunk.WaterRenderNode;
import org.saar.minecraft.chunk.WaterRenderer;
import org.saar.minecraft.components.*;
import org.saar.minecraft.entity.HitBoxes;
import org.saar.minecraft.generator.*;
import org.saar.minecraft.postprocessors.UnderwaterPostProcessor;
import org.saar.minecraft.threading.GlThreadQueue;

public class MinecraftHQ {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final float SPEED = .1f;
    private static final int MOUSE_DELAY = 200;
    private static final float MOUSE_SENSITIVITY = .2f;
    private static final int WORLD_RADIUS = 8;
    private static final int THREAD_COUNT = 5;

    private static final boolean SHADOWS_HQ = true;
    private static final boolean FLY_MODE = true;

    private static final String TEXTURE_ATLAS_PATH = "/minecraft/atlas.png";

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(.0f, .5f, .7f);

        final UIDisplay uiDisplay = new UIDisplay(window);

        final UIElement uiTextContainer = new UIElement();
        uiTextContainer.getStyle().getAlignment().setValue(AlignmentValues.getVertical());
        uiTextContainer.getStyle().getMargin().set(10);
        uiTextContainer.getStyle().getFontSize().set(24);
        uiDisplay.add(uiTextContainer);

        final UIBlock uiTextBackground = new UIBlock();
        uiTextBackground.getStyle().getPosition().setValue(PositionValues.getAbsolute());
        uiTextBackground.getStyle().getBackgroundColour().set(new Colour(255, 255, 255, .5f));
        uiTextBackground.getStyle().getBorderColour().set(Colours.BLACK);
        uiTextBackground.getStyle().getBorders().set(2);
        uiTextBackground.getStyle().getRadius().set(5);
        uiTextContainer.add(uiTextBackground);

        final UIText uiFps = new UIText("Fps: ???");
        uiTextContainer.add(uiFps);

        final UIText uiPosition = new UIText("Position: ???");
        uiTextContainer.add(uiPosition);

        final UIText uiChunk = new UIText("Chunk: ???");
        uiTextContainer.add(uiChunk);

        final UIBlock square = new UIBlock();
        square.getStyle().getBorderColour().set(Colours.DARK_GRAY);
        square.getStyle().getBorders().set(2);
        square.getStyle().getWidth().set(6);
        square.getStyle().getHeight().set(6);
        square.getStyle().getBackgroundColour().set(new Colour(255, 255, 255, .2f));
        square.getStyle().getX().set(CoordinateValues.getCenter());
        square.getStyle().getY().set(CoordinateValues.getCenter());
        square.getStyle().getPosition().setValue(PositionValues.getAbsolute());
        uiDisplay.add(square);

        final World world = buildWorld();
        world.generateAround(Position.of(0, 0, 0), WORLD_RADIUS);

        final Camera camera = buildCamera(window, world);
        final int height = world.getHeight(0, 0) + 10;
        camera.getTransform().getPosition().set(0, height, 0);

        final Texture2D atlas = createAtlas();
        ChunkRenderer.INSTANCE.setAtlas(atlas);
        WaterRenderer.INSTANCE.setAtlas(atlas);

        final DeferredRenderNodeGroup renderNode = new DeferredRenderNodeGroup(
                new ChunkRenderNode(world),
                new WaterRenderNode(world)
        );

        final DirectionalLight sun = new DirectionalLight();
        sun.getDirection().set(-.3f, -1f, -.7f);
        sun.getColour().set(1f, 1f, 1f);

        final int a = 50;
        final OrthographicProjection shadowsProjection =
                new SimpleOrthographicProjection(-a, a, -a, a, -a, a);

        final ShadowsRenderingPath shadowsRenderingPath = new ShadowsRenderingPath(
                ShadowsQuality.HIGH, shadowsProjection, sun, new ChunkRenderNode(world)
        );
        final ReadOnlyTexture2D shadowMap = shadowsRenderingPath.render().getBuffers().getDepth();

        final UIBlock uiShadowMap = new UIBlock();
        uiShadowMap.setTexture(shadowMap);
        uiShadowMap.getStyle().getWidth().set(200);
        uiShadowMap.getStyle().getHeight().set(200);
        uiDisplay.add(uiShadowMap);

        final Fog fog = new Fog(Vector3.of(.0f, .5f, .7f), WORLD_RADIUS * 15, WORLD_RADIUS * 16);

        final DeferredRenderingPipeline pipeline = new DeferredRenderingPipeline(
                new DeferredGeometryPass(renderNode),
                new ShadowsRenderPass(shadowsRenderingPath.getCamera(), shadowMap, sun),
                new UnderwaterPostProcessor(world),
                new FogRenderPass(fog, FogDistance.XZ),
                new GeometryPass2D(uiDisplay),
                new FxaaPostProcessor()
        );

        final DeferredRenderingPath renderingPath = new DeferredRenderingPath(camera, pipeline);

        final Fps fps = new Fps();

        long lastFpsUpdate = System.currentTimeMillis();

        final Mouse mouse = window.getMouse();
        final Keyboard keyboard = window.getKeyboard();

        keyboard.onKeyPress(GLFW.GLFW_KEY_ESCAPE).perform(e ->
                mouse.setCursor(mouse.getCursor() == MouseCursor.DISABLED
                        ? MouseCursor.NORMAL : MouseCursor.DISABLED));
        mouse.hide();

        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlThreadQueue.getInstance().run();
            camera.update();
            renderNode.update();
            uiDisplay.update();

            shadowsRenderingPath.getCamera().getTransform().getPosition().set(camera.getTransform().getPosition());
            shadowsRenderingPath.render();
            renderingPath.render().toMainScreen();

            if (System.currentTimeMillis() - lastFpsUpdate >= 5000) {
                uiFps.setText(String.format("Fps: %.3f", fps.fps()));
                lastFpsUpdate = System.currentTimeMillis();
            }
            uiPosition.setText(String.format("Position: (%.3f,%.3f,%.3f)",
                    camera.getTransform().getPosition().getX(),
                    camera.getTransform().getPosition().getY(),
                    camera.getTransform().getPosition().getZ())
            );
            uiChunk.setText(String.format("Chunk (%d, %d)",
                    World.worldToChunkCoordinate((int) camera.getTransform().getPosition().getX()),
                    World.worldToChunkCoordinate((int) camera.getTransform().getPosition().getZ()))
            );

            window.swapBuffers();
            window.pollEvents();

            if (keyboard.isKeyPressed('R')) {
                PolygonMode.set(Face.FRONT_AND_BACK, PolygonModeValue.LINE);
            } else {
                PolygonMode.set(Face.FRONT_AND_BACK, PolygonModeValue.FILL);
            }

            fps.update();
        }

        atlas.delete();
        world.delete();

        renderingPath.delete();
        window.destroy();
    }

    private static World buildWorld() {
        final WorldGenerator generator = WorldGenerationPipeline
                .pipe(new TerrainGenerator(80, 140, (x, y, z) -> SimplexNoise.noise(x / 64f, y / 64f, z / 64f)))
                .then(new WaterGenerator(100))
                .then(new TreesGenerator(SimplexNoise::noise))
                .then(new BedrockGenerator());
        return new World(generator, THREAD_COUNT, !SHADOWS_HQ);
    }

    private static Camera buildCamera(Window window, World world) {
        final Projection projection = new ScreenPerspectiveProjection(MainScreen.INSTANCE, 70, .20f, 500);
        final NodeComponentGroup cameraComponents = FLY_MODE ?
                new NodeComponentGroup(
                        new GenerateAroundComponent(world, WORLD_RADIUS),
                        new MouseRotationComponent(window.getMouse(), -MOUSE_SENSITIVITY),
                        new PlayerBuildingComponent(window.getMouse(), world, MOUSE_DELAY),
                        new PlayerWalkingComponent(window.getKeyboard(), SPEED),
                        new PlayerFlyingComponent(window.getKeyboard(), SPEED),
                        new CollisionComponent(world, HitBoxes.getPlayer()),
                        new VelocityComponent()
                ) :
                new NodeComponentGroup(
                        new GenerateAroundComponent(world, WORLD_RADIUS),
                        new MouseRotationComponent(window.getMouse(), -MOUSE_SENSITIVITY),
                        new PlayerBuildingComponent(window.getMouse(), world, MOUSE_DELAY),
                        new PlayerWalkingComponent(window.getKeyboard(), SPEED),
                        new GravityComponent(world),
                        new PlayerJumpComponent(world, window.getKeyboard()),
                        new CollisionComponent(world, HitBoxes.getPlayer()),
                        new VelocityComponent()
                );
        return new Camera(projection, cameraComponents);
    }

    private static Texture2D createAtlas() throws Exception {
        final Texture2D atlas = Texture2D.of(TEXTURE_ATLAS_PATH);
        atlas.applyParameters(
                new TextureMagFilterParameter(MagFilterValue.NEAREST),
                new TextureMinFilterParameter(MinFilterValue.NEAREST_MIPMAP_LINEAR),
                new TextureAnisotropicFilterParameter(8)
        );
        atlas.generateMipmap();
        return atlas;
    }
}
