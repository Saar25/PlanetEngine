package org.saar.minecraft.entity;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.core.camera.ICamera;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.BlockContainer;
import org.saar.minecraft.BlockFaceContainer;
import org.saar.minecraft.World;

public final class RayCasting {

    private RayCasting() {
    }

    private static int enterFace(Vector3fc o, Vector3fc d, Vector3fc p) {
        float tMinx = (p.x() - o.x()) / d.x();
        float tMiny = (p.y() - o.y()) / d.y();
        float tMinz = (p.z() - o.z()) / d.z();

        float tMaxx = (p.x() + 1 - o.x()) / d.x();
        float tMaxy = (p.y() + 1 - o.y()) / d.y();
        float tMaxz = (p.z() + 1 - o.z()) / d.z();

        float t1x = Math.min(tMinx, tMaxx);
        float t1y = Math.min(tMiny, tMaxy);
        float t1z = Math.min(tMinz, tMaxz);

        if (t1x >= t1y && t1x >= t1z)
            return d.x() > 0 ? 0 : 1;

        if (t1y >= t1z && t1y >= t1x)
            return d.y() > 0 ? 2 : 3;

        /*if (t1z >= t1x && t1z >= t1y)*/
        return d.z() > 0 ? 4 : 5;
    }

    public static BlockFaceContainer lookingAtFace(ICamera camera, World world, int maxSteps) {
        float big = 1E30f;

        final Vector3f d = Vector3.of(camera.getTransform().getRotation().getDirection()).mul(-1).normalize();
        final Vector3f o = Vector3.of(camera.getTransform().getPosition().getValue());

        final Vector3fc di = Vector3.of(1).div(d);

        final Vector3fc s = Vector3.of(
                d.x() > 0 ? 1 : -1,
                d.y() > 0 ? 1 : -1,
                d.z() > 0 ? 1 : -1);

        final Vector3fc dt = Vector3.of(
                Math.min(di.x() * s.x(), big),
                Math.min(di.y() * s.y(), big),
                Math.min(di.z() * s.z(), big));

        final Vector3f p = Vector3.of(o).floor();

        final Vector3f t = Vector3.of(s).max(Vector3.ZERO)
                .add(p).sub(o).mul(di).absolute();

        for (int i = 0; i < maxSteps; i++) {
            final BlockContainer block = world.getBlockContainer(p.x, p.y, p.z);

            if (block.getBlock().isCollideable()) {
                final int face = enterFace(o, d, p);
                return new BlockFaceContainer((int) p.x, (int) p.y, (int) p.z, block.getBlock(), face, -1);
            }
            final int xCmp = t.z < t.x || t.y < t.x ? 0 : 1;
            final int yCmp = t.x < t.y || t.z < t.y ? 0 : 1;
            final int zCmp = t.y < t.z || t.x < t.z ? 0 : 1;
            t.add(dt.x() * xCmp, dt.y() * yCmp, dt.z() * zCmp);
            p.add(s.x() * xCmp, s.y() * yCmp, s.z() * zCmp);
        }
        return null;
    }
}
