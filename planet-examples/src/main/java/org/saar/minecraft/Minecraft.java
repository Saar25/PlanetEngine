package org.saar.minecraft;

import org.joml.SimplexNoise;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.MouseRotationComponent;
import org.saar.core.common.components.VelocityComponent;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.gui.*;
import org.saar.gui.style.Colour;
import org.saar.gui.style.Colours;
import org.saar.gui.style.alignment.AlignmentValues;
import org.saar.gui.style.coordinate.CoordinateValues;
import org.saar.gui.style.length.LengthValues;
import org.saar.gui.style.position.PositionValues;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.input.mouse.MouseCursor;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.constants.Face;
import org.saar.lwjgl.opengl.polygonmode.PolygonMode;
import org.saar.lwjgl.opengl.polygonmode.PolygonModeValue;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.lwjgl.opengl.texture.parameter.TextureAnisotropicFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter;
import org.saar.lwjgl.opengl.texture.values.MagFilterValue;
import org.saar.lwjgl.opengl.texture.values.MinFilterValue;
import org.saar.maths.noise.Noise3f;
import org.saar.maths.transform.Position;
import org.saar.minecraft.chunk.ChunkRenderer;
import org.saar.minecraft.chunk.WaterRenderer;
import org.saar.minecraft.components.*;
import org.saar.minecraft.entity.HitBoxes;
import org.saar.minecraft.generator.*;
import org.saar.minecraft.threading.GlThreadQueue;

import java.util.concurrent.CompletableFuture;

public class Minecraft {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final float SPEED = .1f;
    private static final int MOUSE_DELAY = 200;
    private static final float MOUSE_SENSITIVITY = .2f;
    private static final int WORLD_RADIUS = 5;
    private static final int THREAD_COUNT = 5;

    private static final boolean HIGH_QUALITY = false;

    private static final boolean FLY_MODE = true;

    private static final String TEXTURE_ATLAS_PATH = "/minecraft/atlas.png";

    private static boolean uiMode = false;

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(.0f, .5f, .7f);

        final UIDisplay uiDisplay = new UIDisplay(window);

        final UIElement uiTextContainer = new UIElement();
        uiTextContainer.getStyle().getAlignment().setValue(AlignmentValues.vertical);
        uiTextContainer.getStyle().getFontSize().set(24);
        uiTextContainer.getStyle().getBackgroundColour().set(new Colour(255, 255, 255, .5f));
        uiTextContainer.getStyle().getBorderColour().set(Colours.BLACK);
        uiTextContainer.getStyle().getBorders().set(2);
        uiTextContainer.getStyle().getRadius().set(5);
        uiTextContainer.getStyle().getMargin().set(10);
        uiTextContainer.getStyle().getPadding().set(10);
        uiDisplay.add(uiTextContainer);

        final UIText uiFps = new UIText("Fps: ???");
        uiTextContainer.add(uiFps);

        final UIText uiPosition = new UIText("Position: ???");
        uiTextContainer.add(uiPosition);

        final UIText uiChunk = new UIText("Chunk: ???");
        uiTextContainer.add(uiChunk);

        final UIChildNode inventory = buildInventory();

        final UIBlock square = new UIBlock();
        square.getStyle().getBorderColour().set(Colours.DARK_GRAY);
        square.getStyle().getBorders().set(2);
        square.getStyle().getWidth().set(6);
        square.getStyle().getHeight().set(6);
        square.getStyle().getBackgroundColour().set(new Colour(255, 255, 255, .2f));
        square.getStyle().getX().set(CoordinateValues.center);
        square.getStyle().getY().set(CoordinateValues.center);
        square.getStyle().getPosition().setValue(PositionValues.absolute);
        uiDisplay.add(square);

        final World world = buildWorld();
        final CompletableFuture<Void> worldGeneratingFuture =
                world.generateAround(Position.of(0, 0, 0), WORLD_RADIUS);
        while (!worldGeneratingFuture.isDone()) {
            window.swapBuffers();
            window.pollEvents();
        }

        final Camera camera = buildCamera(window, world);
        final int height = world.getHeight(0, 0) + 10;
        camera.getTransform().getPosition().set(0, height, 0);

        final MinecraftRendering rendering = HIGH_QUALITY
                ? new MinecraftDeferredRendering(uiDisplay, world, camera, WORLD_RADIUS)
                : new MinecraftForwardRendering(uiDisplay, world, camera, WORLD_RADIUS);

        final Texture2D atlas = createAtlas();
        ChunkRenderer.INSTANCE.setAtlas(atlas);
        WaterRenderer.INSTANCE.setAtlas(atlas);

        final RenderingPath<?> renderingPath = rendering.buildRenderingPath();

        final Fps fps = new Fps();

        long lastFpsUpdate = System.currentTimeMillis();

        final Mouse mouse = window.getMouse();
        final Keyboard keyboard = window.getKeyboard();

        mouse.hide();

        keyboard.onKeyPress('E').perform(e -> {
            uiMode = !uiMode;
            if (uiMode) {
                mouse.setCursor(MouseCursor.NORMAL);
                uiDisplay.add(inventory);
            } else {
                mouse.setCursor(MouseCursor.DISABLED);
                uiDisplay.remove(inventory);
            }
        });

        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlThreadQueue.getInstance().run();
            camera.update();
            uiDisplay.update();

            rendering.update();
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

    private static UIChildNode buildInventory() {
        final UIElement uiInventory = new UIElement();
        uiInventory.getStyle().getPosition().setValue(PositionValues.absolute);
        uiInventory.getStyle().getX().set(CoordinateValues.center);
        uiInventory.getStyle().getY().set(CoordinateValues.center);
        uiInventory.getStyle().getWidth().set(LengthValues.percent(50));
        uiInventory.getStyle().getHeight().set(LengthValues.ratio(.8f));
        uiInventory.getStyle().getAlignment().setValue(AlignmentValues.vertical);
        uiInventory.getStyle().getRadius().set(10);
        uiInventory.getStyle().getPadding().set(10);
        uiInventory.getStyle().getBorderColour().set(Colours.TRANSPARENT);
        uiInventory.getStyle().getBackgroundColour().set(Colours.GRAY);

        final UIElement uiTop = new UIElement();
        uiTop.getStyle().getHeight().set(LengthValues.percent(40));
        uiInventory.add(uiTop);

        final UIElement uiItems = new UIElement();
        uiItems.getStyle().getAlignment().setValue(AlignmentValues.vertical);
        for (int row = 0; row < 4; row++) {
            final UIElement uiItemsRow = new UIElement();
            uiItemsRow.getStyle().getWidth().set(LengthValues.percent(100));
            if (row == 3) uiItemsRow.getStyle().getMargin().set(10, 0, 0, 0);
            for (int col = 0; col < 9; col++) {
                final UIBlock uiBlock = new UIBlock();
                uiBlock.getStyle().getWidth().setValue(LengthValues.percent(10));
                uiBlock.getStyle().getHeight().setValue(LengthValues.ratio(1));
                uiBlock.getStyle().getBorderColour().set(Colours.DARK_GRAY);
                uiBlock.getStyle().getBorders().set(3);
                uiItemsRow.add(uiBlock);
            }
            uiItems.add(uiItemsRow);
        }
        uiInventory.add(uiItems);

        return uiInventory;
    }

    private static World buildWorld() {
        final Noise3f noise3f = (x, y, z) -> SimplexNoise.noise(x / 32f, y / 32f, z / 32f);
        final WorldGenerator generator = WorldGenerationPipeline
                .pipe(new BedrockGenerator())
                .then(new Terrain3DGenerator(60, 140, noise3f))
                .then(new WaterGenerator(100))
                .then(new TreesGenerator(SimplexNoise::noise));
        return new World(generator, THREAD_COUNT);
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
                        new GravityComponent(),
                        new PlayerJumpComponent(window.getKeyboard()),
                        new WaterFloatingComponent(),
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
