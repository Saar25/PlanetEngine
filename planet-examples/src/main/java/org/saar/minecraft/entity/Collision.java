package org.saar.minecraft.entity;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.ReadonlyPosition;
import org.saar.maths.utils.Maths;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.BlockContainer;
import org.saar.minecraft.World;

public final class Collision {

    private static final float EPSILON = .001f;

    private Collision() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    private static float safeClamp(float value, float max) {
        return max > 0 ? Maths.clamp(value, 0, max) : Maths.clamp(value, max, 0);
    }

    private static BlockContainer collidedBlock(World world, ReadonlyPosition position, Vector3fc unitDirection, int length) {
        final Position futurePosition = Position.create();
        final Vector3f temp = Vector3.create();
        for (int i = 1; i <= length; i++) {
            futurePosition.set(position);
            temp.set(unitDirection).mul(i);
            futurePosition.add(temp);

            final BlockContainer block = world.getBlockContainer(futurePosition);
            if (block.getBlock().isCollideable()) {
                return block;
            }
        }
        return null;
    }

    public static void ensureDirection(World world, ReadonlyPosition position, Vector3f direction) {
        final Vector3f[] unitDirections = {
                Vector3.of(direction.x(), 0, 0),
                Vector3.of(0, direction.y(), 0),
                Vector3.of(0, 0, direction.z()),
        };

        final Vector3f normal = Vector3.create();
        for (int i = 0; i < unitDirections.length; i++) {
            final Vector3f unitDirection = unitDirections[i];
            final int length = (int) Math.ceil(Math.abs(unitDirection.get(i)));
            final BlockContainer block = collidedBlock(world, position, unitDirection, length);

            if (block != null && block.getBlock().isCollideable()) {
                normal.set(-Math.signum(unitDirection.x),
                        -Math.signum(unitDirection.y),
                        -Math.signum(unitDirection.z));

                final float face = block.getPosition().get(i) + .5f + normal.get(i) * (.5f + EPSILON);
                final float distance = face - position.getValue().get(i);
                direction.setComponent(i, safeClamp(direction.get(i), distance));
            }
        }
    }
}
