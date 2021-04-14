package org.saar.minecraft;

import org.joml.SimplexNoise;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4i;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.postprocessing.processors.ContrastPostProcessor;
import org.saar.core.renderer.RenderersGroup;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.renderer.forward.ForwardRenderingPath;
import org.saar.core.renderer.forward.ForwardScreenPrototype;
import org.saar.core.screen.MainScreen;
import org.saar.core.screen.annotations.ScreenImageProperty;
import org.saar.core.screen.image.ColourScreenImage;
import org.saar.core.screen.image.DepthScreenImage;
import org.saar.core.screen.image.ScreenImage;
import org.saar.core.util.Fps;
import org.saar.gui.objects.TSquare;
import org.saar.gui.render.GuiRenderer;
import org.saar.gui.style.property.Colours;
import org.saar.lwjgl.glfw.event.EventListener;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.input.mouse.MouseButton;
import org.saar.lwjgl.glfw.input.mouse.MouseCursor;
import org.saar.lwjgl.glfw.input.mouse.MoveEvent;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.attachment.DepthAttachment;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter;
import org.saar.lwjgl.opengl.textures.settings.*;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.chunk.ChunkRenderer;
import org.saar.minecraft.chunk.WaterRenderer;
import org.saar.minecraft.entity.Player;
import org.saar.minecraft.generator.*;
import org.saar.minecraft.threading.GlThreadQueue;

public class Minecraft {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 741;
    private static final float SPEED = .1f;
    private static final int MOUSE_DELAY = 200;
    private static final float MOUSE_SENSITIVITY = .2f;
    private static final int WORLD_RADIUS = 3;
    private static final int THREAD_COUNT = 5;

    private static final boolean FLY_MODE = true;

    private static final WorldGenerator generator = WorldGenerationPipeline
            .pipe(new TerrainGenerator(80))
            .then(new WaterGenerator(80))
            .then(new TreesGenerator(SimplexNoise::noise))
            .then(new BedrockGenerator());

    private static final World world = new World(generator, THREAD_COUNT);

    private static final String TEXTURE_ATLAS_PATH = "/minecraft/atlas.png";

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        GlUtils.setClearColour(.0f, .5f, .7f);

        final TSquare square = new TSquare(12, 12);
        square.getStyle().borderColour.set(Colours.DARK_GREY);
        square.getStyle().borders.set(2);
        final GuiRenderer guiRenderer = new GuiRenderer(square);

        final Projection projection = new ScreenPerspectiveProjection(MainScreen.getInstance(), 70, .20f, 500);
        final Camera camera = new Camera(projection);
        final Player player = new Player(camera);

        world.generateAround(camera.getTransform().getPosition(), WORLD_RADIUS);

        final Texture2D textureAtlas = Texture2D.of(TEXTURE_ATLAS_PATH);
        textureAtlas.setSettings(
                new TextureMagFilterSetting(MagFilterParameter.NEAREST),
                new TextureMinFilterSetting(MinFilterParameter.NEAREST_MIPMAP_LINEAR),
                new TextureAnisotropicFilterSetting(4),
                new TextureMipMapSetting(),
                new TextureSWrapSetting(WrapParameter.CLAMP_TO_BORDER),
                new TextureTWrapSetting(WrapParameter.CLAMP_TO_BORDER),
                new TextureBorderColourSetting(0, 0, 0, 0)
        );
        final ChunkRenderer renderer = new ChunkRenderer(world, textureAtlas);
        final WaterRenderer waterRenderer = new WaterRenderer(world, textureAtlas);

        camera.getTransform().getPosition().set(0, 100, 0);

        final Mouse mouse = window.getMouse();
        mouse.addMoveListener(new EventListener<MoveEvent>() {
            private double xOld = mouse.getXPos();
            private double yOld = mouse.getYPos();

            @Override
            public void onEvent(MoveEvent e) {
                if (mouse.getCursor() == MouseCursor.DISABLED) {
                    final float yRotate = (float) (this.xOld - mouse.getXPos()) * MOUSE_SENSITIVITY;
                    final float xRotate = (float) (this.yOld - mouse.getYPos()) * MOUSE_SENSITIVITY;
                    camera.getTransform().getRotation().rotateDegrees(xRotate, yRotate, 0);
                }
                this.xOld = mouse.getXPos();
                this.yOld = mouse.getYPos();
            }
        });
        mouse.hide();

        final Position lastWorldUpdatePosition = Position.of(
                camera.getTransform().getPosition().getValue());

        final PostProcessingPipeline postProcessing = new PostProcessingPipeline(
                new ContrastPostProcessor(1.3f)
        );

        final RenderingPath renderingPath = new ForwardRenderingPath(new ForwardScreenPrototype() {
            private final Texture colourTexture = Texture.create(TextureTarget.TEXTURE_2D);

            @ScreenImageProperty
            private final ScreenImage colourImage = new ColourScreenImage(ColourAttachment.withTexture(
                    0, this.colourTexture, ColourFormatType.RGB16, FormatType.RGB, DataType.U_BYTE));

            @ScreenImageProperty
            private final ScreenImage depthImage = new DepthScreenImage(
                    DepthAttachment.withRenderBuffer(DepthFormatType.COMPONENT16));

            @Override
            public ReadOnlyTexture getColourTexture() {
                return this.colourTexture;
            }
        }, camera, new RenderersGroup(renderer, waterRenderer, guiRenderer));

        final Fps fps = new Fps();

        long lastBlockPlace = System.currentTimeMillis();

        final Keyboard keyboard = window.getKeyboard();
        keyboard.onKeyPress(GLFW.GLFW_KEY_ESCAPE).perform(e ->
                mouse.setCursor(mouse.getCursor() == MouseCursor.DISABLED
                        ? MouseCursor.NORMAL : MouseCursor.DISABLED));

        while (window.isOpen() && !keyboard.isKeyPressed('T')) {
            GlThreadQueue.getInstance().run();

            postProcessing.process(renderingPath.render().toTexture()).toMainScreen();

            window.update(true);
            window.pollEvents();

            final Vector3f direction = Vector3.create();
            if (keyboard.isKeyPressed('W')) {
                direction.add(0, 0, -1);
            }
            if (keyboard.isKeyPressed('A')) {
                direction.add(-1, 0, 0);
            }
            if (keyboard.isKeyPressed('S')) {
                direction.add(0, 0, +1);
            }
            if (keyboard.isKeyPressed('D')) {
                direction.add(+1, 0, 0);
            }

            direction.rotate(camera.getTransform().getRotation().getValue()).y = 0;

            final float speed = keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL) ? SPEED * 5 : SPEED;

            if (!FLY_MODE) {
                if (keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    player.jump(world);
                }
                if (direction.lengthSquared() > 0) {
                    direction.normalize(speed);
                }
                player.move(world, direction, (float) fps.delta());
            } else {
                if (direction.lengthSquared() > 0) {
                    direction.normalize(speed);
                }

                if (keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                    direction.add(0, -SPEED, 0);
                }
                if (keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    direction.add(0, +SPEED, 0);
                }
                player.fly(world, direction);
            }

            final BlockFaceContainer rayCast = player.rayCast(world);
            if (rayCast != null && rayCast.getBlock().isCollideable()) {
                renderer.getRayCastedFace().setValue(new Vector4i(rayCast.getX(),
                        rayCast.getY(), rayCast.getZ(), rayCast.getDirection()));

                if (lastBlockPlace + MOUSE_DELAY <= System.currentTimeMillis()) {
                    if (mouse.isButtonDown(MouseButton.PRIMARY)) {
                        lastBlockPlace = System.currentTimeMillis();

                        world.setBlock(rayCast.getX(), rayCast.getY(), rayCast.getZ(), Blocks.AIR);
                    }
                    if (mouse.isButtonDown(MouseButton.SECONDARY)) {
                        lastBlockPlace = System.currentTimeMillis();

                        final Vector3i blockDirection = new Vector3i[]{
                                new Vector3i(-1, 0, 0), new Vector3i(+1, 0, 0),
                                new Vector3i(0, -1, 0), new Vector3i(0, +1, 0),
                                new Vector3i(0, 0, -1), new Vector3i(0, 0, +1)}[rayCast.getDirection()]
                                .add(rayCast.getPosition());
                        world.setBlock(blockDirection.x, blockDirection.y, blockDirection.z, Blocks.STONE);
                    }
                }
            } else {
                renderer.getRayCastedFace().setValue(new Vector4i(0, 0, 0, -1));
            }

            final float xChange = lastWorldUpdatePosition.getX() - camera.getTransform().getPosition().getX();
            final float zChange = lastWorldUpdatePosition.getZ() - camera.getTransform().getPosition().getZ();
            if (Math.abs(xChange) > WORLD_RADIUS * 2 || Math.abs(zChange) > WORLD_RADIUS * 2) {
                lastWorldUpdatePosition.set(camera.getTransform().getPosition());
                world.generateAround(lastWorldUpdatePosition, WORLD_RADIUS);
            }

            if (keyboard.isKeyPressed('R')) {
                GlUtils.drawPolygonLine();
            } else {
                GlUtils.drawPolygonFill();
            }

            square.getStyle().x.set(window.getWidth() / 2 - 6);
            square.getStyle().y.set(window.getHeight() / 2 - 6);

            System.out.print("\r" + String.format("%.3f", fps.fps()));
            fps.update();
        }

        textureAtlas.delete();
        world.delete();

        guiRenderer.delete();
        renderer.delete();
        waterRenderer.delete();
        window.destroy();
    }

}