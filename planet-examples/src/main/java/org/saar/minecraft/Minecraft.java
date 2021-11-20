package org.saar.minecraft;

import org.joml.SimplexNoise;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4i;
import org.lwjgl.glfw.GLFW;
import org.saar.core.camera.Camera;
import org.saar.core.camera.Projection;
import org.saar.core.camera.projection.ScreenPerspectiveProjection;
import org.saar.core.common.components.MouseRotationComponent;
import org.saar.core.node.NodeComponentGroup;
import org.saar.core.postprocessing.processors.FxaaPostProcessor;
import org.saar.core.renderer.RenderContext;
import org.saar.core.renderer.forward.ForwardRenderNodeGroup;
import org.saar.core.renderer.forward.ForwardRenderingPath;
import org.saar.core.renderer.forward.ForwardRenderingPipeline;
import org.saar.core.renderer.forward.passes.ForwardGeometryPass;
import org.saar.core.screen.MainScreen;
import org.saar.core.util.Fps;
import org.saar.gui.UIBlockElement;
import org.saar.gui.UIDisplay;
import org.saar.gui.style.Colours;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.input.mouse.MouseButton;
import org.saar.lwjgl.glfw.input.mouse.MouseCursor;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.clear.ClearColour;
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D;
import org.saar.lwjgl.opengl.texture.Texture2D;
import org.saar.lwjgl.opengl.texture.parameter.*;
import org.saar.lwjgl.opengl.texture.values.MagFilterValue;
import org.saar.lwjgl.opengl.texture.values.MinFilterValue;
import org.saar.lwjgl.opengl.texture.values.WrapValue;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.chunk.ChunkRenderNode;
import org.saar.minecraft.chunk.ChunkRenderer;
import org.saar.minecraft.chunk.WaterRenderNode;
import org.saar.minecraft.chunk.WaterRenderer;
import org.saar.minecraft.entity.Player;
import org.saar.minecraft.generator.*;
import org.saar.minecraft.postprocessors.UnderwaterPostProcessor;
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

    private static final String TEXTURE_ATLAS_PATH = "/minecraft/atlas.png";

    public static void main(String[] args) throws Exception {
        final Window window = Window.create("Lwjgl", WIDTH, HEIGHT, true);

        ClearColour.set(.0f, .5f, .7f);

        final UIDisplay uiDisplay = new UIDisplay(window);

        final UIBlockElement square = new UIBlockElement();
        square.getStyle().getBorderColour().set(Colours.DARK_GRAY);
        square.getStyle().getBorders().set(2);
        square.getStyle().getWidth().set(12);
        square.getStyle().getHeight().set(12);

        uiDisplay.add(square);

        final Projection projection = new ScreenPerspectiveProjection(MainScreen.INSTANCE, 70, .20f, 500);
        final NodeComponentGroup cameraComponents = new NodeComponentGroup(
                new MouseRotationComponent(window.getMouse(), -MOUSE_SENSITIVITY)
        );
        final Camera camera = new Camera(projection, cameraComponents);
        final Player player = new Player(camera);

        final WorldGenerator generator = WorldGenerationPipeline
                .pipe(new TerrainGenerator(80))
                .then(new WaterGenerator(80))
                .then(new TreesGenerator(SimplexNoise::noise))
                .then(new BedrockGenerator());
        final World world = new World(generator, THREAD_COUNT);
        world.generateAround(camera.getTransform().getPosition(), WORLD_RADIUS);

        final Texture2D textureAtlas = Texture2D.of(TEXTURE_ATLAS_PATH);
        textureAtlas.applyParameters(
                new TextureMagFilterParameter(MagFilterValue.NEAREST),
                new TextureMinFilterParameter(MinFilterValue.NEAREST_MIPMAP_LINEAR),
                new TextureAnisotropicFilterParameter(4),
                new TextureSWrapParameter(WrapValue.CLAMP_TO_BORDER),
                new TextureTWrapParameter(WrapValue.CLAMP_TO_BORDER),
                new TextureBorderColourParameter(0, 0, 0, 0)
        );
        textureAtlas.generateMipmap();

        ChunkRenderer.INSTANCE.setAtlas(textureAtlas);
        WaterRenderer.INSTANCE.setAtlas(textureAtlas);

        camera.getTransform().getPosition().set(0, 100, 0);

        final Position lastWorldUpdatePosition = Position.of(
                camera.getTransform().getPosition().getValue());

        final ForwardRenderNodeGroup renderNode = new ForwardRenderNodeGroup(
                new ChunkRenderNode(world),
                new WaterRenderNode(world)
        );

        final UnderwaterPostProcessor underwaterPass = new UnderwaterPostProcessor();
        final ForwardGeometryPass geometryPass = new ForwardGeometryPass(renderNode);
        final FxaaPostProcessor fxaaPass = new FxaaPostProcessor();

        final ForwardRenderingPipeline pipeline = new ForwardRenderingPipeline(geometryPass, fxaaPass);
        final ForwardRenderingPipeline underwater = new ForwardRenderingPipeline(geometryPass, underwaterPass, fxaaPass);

        final ForwardRenderingPath renderingPath = new ForwardRenderingPath(camera, pipeline);

        final Fps fps = new Fps();

        long lastBlockPlace = System.currentTimeMillis();

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

            if (world.getBlock(camera.getTransform().getPosition()) == Blocks.WATER) {
                final ReadOnlyTexture2D rendererTexture = renderingPath.render().getBuffers().getAlbedo();

                MainScreen.INSTANCE.setAsDraw();
                underwaterPass.render(new RenderContext(camera), () -> rendererTexture);
            } else {
                renderingPath.render().toMainScreen();
            }
            uiDisplay.render(new RenderContext(camera));

            window.swapBuffers();
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
                ChunkRenderer.INSTANCE.getRayCastedFace().setValue(new Vector4i(rayCast.getX(),
                        rayCast.getY(), rayCast.getZ(), rayCast.getDirection()));

                if (lastBlockPlace + MOUSE_DELAY <= System.currentTimeMillis()) {
                    if (mouse.isButtonDown(MouseButton.getPRIMARY())) {
                        lastBlockPlace = System.currentTimeMillis();

                        world.setBlock(rayCast.getX(), rayCast.getY(), rayCast.getZ(), Blocks.AIR);
                    }
                    if (mouse.isButtonDown(MouseButton.getSECONDARY())) {
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
                ChunkRenderer.INSTANCE.getRayCastedFace().setValue(new Vector4i(0, 0, 0, -1));
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

            square.getStyle().getX().set(window.getWidth() / 2 - 6);
            square.getStyle().getY().set(window.getHeight() / 2 - 6);

            System.out.print("\r" + String.format("%.3f", fps.fps()));
            fps.update();
        }

        textureAtlas.delete();
        world.delete();

        underwaterPass.delete();
        renderingPath.delete();
        window.destroy();
    }
}