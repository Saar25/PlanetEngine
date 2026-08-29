package org.saar.minecraft.entity

import org.joml.Vector3fc
import org.saar.maths.transform.Transform
import org.saar.maths.utils.Vector3
import org.saar.maths.utils.Vector3.of
import org.saar.minecraft.BlockFaceContainer
import org.saar.minecraft.World
import kotlin.math.min

object RayCasting {
    private fun enterFace(o: Vector3fc, d: Vector3fc, p: Vector3fc): Int {
        val tMinx = (p.x() - o.x()) / d.x()
        val tMiny = (p.y() - o.y()) / d.y()
        val tMinz = (p.z() - o.z()) / d.z()

        val tMaxx = (p.x() + 1 - o.x()) / d.x()
        val tMaxy = (p.y() + 1 - o.y()) / d.y()
        val tMaxz = (p.z() + 1 - o.z()) / d.z()

        val t1x = min(tMinx, tMaxx)
        val t1y = min(tMiny, tMaxy)
        val t1z = min(tMinz, tMaxz)

        if (t1x >= t1y && t1x >= t1z) return if (d.x() < 0) 0 else 1

        if (t1y >= t1z && t1y >= t1x) return if (d.y() < 0) 2 else 3

        /*if (t1z >= t1x && t1z >= t1y)*/
        return if (d.z() < 0) 4 else 5
    }

    fun lookingAtFace(transform: Transform, world: World, maxSteps: Int): BlockFaceContainer? {
        val big = 1E30f

        val d = of(transform.rotation.direction).mul(-1f).normalize()
        val o = of(transform.position.getValue())

        val di: Vector3fc = of(1f).div(d)

        val s: Vector3fc = of(
            (if (d.x() > 0) 1 else -1).toFloat(),
            (if (d.y() > 0) 1 else -1).toFloat(),
            (if (d.z() > 0) 1 else -1).toFloat()
        )

        val dt: Vector3fc = of(
            min(di.x() * s.x(), big),
            min(di.y() * s.y(), big),
            min(di.z() * s.z(), big)
        )

        val p = of(o).floor()

        val t = of(s).max(Vector3.ZERO)
            .add(p).sub(o).mul(di).absolute()

        for (i in 0..<maxSteps) {
            val block = world.getBlockContainer(p.x, p.y, p.z)

            if (block.block.isCollideable) {
                val face = enterFace(o, d, p)
                return BlockFaceContainer(
                    p.x.toInt(),
                    p.y.toInt(),
                    p.z.toInt(),
                    block.block,
                    face,
                    -1,
                    BooleanArray(4)
                )
            }
            val xCmp = if (t.z < t.x || t.y < t.x) 0 else 1
            val yCmp = if (t.x < t.y || t.z < t.y) 0 else 1
            val zCmp = if (t.y < t.z || t.x < t.z) 0 else 1
            t.add(dt.x() * xCmp, dt.y() * yCmp, dt.z() * zCmp)
            p.add(s.x() * xCmp, s.y() * yCmp, s.z() * zCmp)
        }
        return null
    }
}
