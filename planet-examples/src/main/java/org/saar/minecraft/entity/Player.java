package org.saar.minecraft.entity;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.core.camera.Camera;
import org.saar.maths.transform.Position;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.Block;
import org.saar.minecraft.BlockFaceContainer;
import org.saar.minecraft.World;

public class Player {

    private final Camera camera;

    private float yVelocity = 0;

    public Player(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return this.camera;
    }

    private boolean isOnBlock(World world) {
        final Position position = getCamera().getTransform().getPosition();
        final Block block = world.getBlock((int) Math.floor(position.getX()),
                (int) Math.floor(position.getY() - 1.51f), (int) Math.floor(position.getZ()));
        return block.isCollideable();
    }

    public void jump(World world) {
        if (isOnBlock(world)) {
            this.yVelocity = .25f;
        }
    }

    public void move(World world, Vector3fc direction, float delta) {

        final Position cameraPosition = getCamera().getTransform().getPosition();

        final Vector3f ensured = HitBoxes.getPlayer().collideWithWorld(
                world, cameraPosition, Vector3.of(direction).add(0, this.yVelocity, 0));

        getCamera().getTransform().getPosition().add(ensured);

        if (isOnBlock(world) || ensured.y == 0) {
            this.yVelocity = 0;
        }
        this.yVelocity -= .98f * delta;
    }

    public BlockFaceContainer rayCast(World world) {
        return RayCasting.lookingAtFace(camera, world, 10);
    }
}
