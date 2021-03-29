package org.saar.minecraft.entity;

import org.joml.RayAabIntersection;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.saar.core.camera.ICamera;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.BlockContainer;
import org.saar.minecraft.BlockFaceContainer;
import org.saar.minecraft.Blocks;
import org.saar.minecraft.World;

public final class RayCasting {

    private RayCasting() {
    }

    private static boolean testBlock(RayAabIntersection intersection, BlockContainer block) {
        return intersection.test(block.getX(), block.getY(), block.getZ(),
                block.getX() + 1, block.getY() + 1, block.getZ() + 1);
    }

    private static boolean testFace(RayAabIntersection intersection, BlockContainer block, int x, int y, int z) {
        return intersection.test(block.getX(), block.getY(), block.getZ(),
                block.getX() + 1 - x, block.getY() + 1 - y, block.getZ() + 1 - z);
    }

    public static BlockFaceContainer lookingAtFace(ICamera camera, World world, int maxSteps) {
        final Vector3f direction = Vector3.of(camera.getTransform().getRotation().getDirection()).mul(-1);
        final Vector3f position = Vector3.of(camera.getTransform().getPosition().getValue());

        final Vector3i step = new Vector3i(
                (int) Math.signum(direction.x),
                (int) Math.signum(direction.y),
                (int) Math.signum(direction.z));

        for (int i = 0; i < maxSteps; i++) {
            final RayAabIntersection intersection = new RayAabIntersection(
                    position.x, position.y, position.z,
                    direction.x, direction.y, direction.z
            );

            final BlockContainer block = world.getBlockContainer(
                    position.x, position.y, position.z);
            if (block.getBlock() != Blocks.AIR && testBlock(intersection, block)) {
                if (testFace(intersection, block, (1 - step.x) / 2, 0, 0)) {
                    return new BlockFaceContainer(block.getX(), block.getY(),
                            block.getZ(), block.getBlock(), (1 - step.x) / 2);
                }
                if (testFace(intersection, block, 0, (1 - step.y) / 2, 0)) {
                    return new BlockFaceContainer(block.getX(), block.getY(),
                            block.getZ(), block.getBlock(), 2 + (1 - step.y) / 2);
                }
                if (testFace(intersection, block, 0, 0, (1 - step.z) / 2)) {
                    return new BlockFaceContainer(block.getX(), block.getY(),
                            block.getZ(), block.getBlock(), 4 + (1 - step.z) / 2);
                }
                // ERROR
                return new BlockFaceContainer(block.getX(), block.getY(),
                        block.getZ(), block.getBlock(), (1 - step.x) / 2);
            }

            final BlockContainer xBlock = world.getBlockContainer(
                    position.x + step.x, position.y, position.z);
            if (xBlock.getBlock() != Blocks.AIR && testBlock(intersection, xBlock)) {
                return new BlockFaceContainer(xBlock.getX(), xBlock.getY(),
                        xBlock.getZ(), xBlock.getBlock(), (1 - step.x) / 2);
            }

            final BlockContainer yBlock = world.getBlockContainer(
                    position.x, position.y + step.y, position.z);
            if (yBlock.getBlock() != Blocks.AIR && testBlock(intersection, yBlock)) {
                return new BlockFaceContainer(yBlock.getX(), yBlock.getY(),
                        yBlock.getZ(), yBlock.getBlock(), 2 + (1 - step.y) / 2);
            }

            final BlockContainer zBlock = world.getBlockContainer(
                    position.x, position.y, position.z + step.z);
            if (zBlock.getBlock() != Blocks.AIR && testBlock(intersection, zBlock)) {
                return new BlockFaceContainer(zBlock.getX(), zBlock.getY(),
                        zBlock.getZ(), zBlock.getBlock(), 4 + (1 - step.z) / 2);
            }

            position.add(direction);
        }
        return null;
    }
}
