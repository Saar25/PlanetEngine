package org.saar.minecraft.entity

import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.Vector3ic
import org.saar.maths.transform.Position
import org.saar.maths.transform.ReadonlyPosition
import org.saar.maths.utils.Maths.clamp
import org.saar.maths.utils.Vector3
import org.saar.maths.utils.Vector3.of
import org.saar.minecraft.BlockContainer
import org.saar.minecraft.World
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sign

object Collision {
    private const val EPSILON = .001f

    private fun isBetween(value: Float, min: Float, max: Float): Boolean {
        return value in min..max
    }

    private fun safeClamp(value: Float, max: Float): Float {
        return if (max > 0) clamp(value, 0f, max) else clamp(value, max, 0f)
    }

    private fun collidedBlock(
        world: World,
        position: ReadonlyPosition,
        unitDirection: Vector3fc?,
        length: Int
    ): BlockContainer? {
        val futurePosition = Position.create()
        val temp = Vector3.create()
        for (i in 1..length) {
            futurePosition.set(position)
            temp.set(unitDirection).mul(i.toFloat())
            futurePosition.add(temp)

            val block = world.getBlockContainer(futurePosition)
            if (block.block.isCollideable) {
                return block
            }
        }
        return null
    }

    fun ensureDirection(world: World, position: ReadonlyPosition, direction: Vector3f) {
        val unitDirections = arrayOf<Vector3f>(
            of(direction.x(), 0f, 0f),
            of(0f, direction.y(), 0f),
            of(0f, 0f, direction.z()),
        )

        val normal = Vector3.create()
        for (i in unitDirections.indices) {
            val unitDirection = unitDirections[i]
            val length = ceil(abs(unitDirection.get(i)).toDouble()).toInt()
            val block: BlockContainer? = collidedBlock(world, position, unitDirection, length)

            if (block != null && block.block.isCollideable) {
                normal.set(
                    -sign(unitDirection.x),
                    -sign(unitDirection.y),
                    -sign(unitDirection.z)
                )

                val face: Float = block.position.get(i) + .5f + normal.get(i) * (.5f + EPSILON)
                val distance = face - position.getValue().get(i)
                direction.setComponent(i, safeClamp(direction.get(i), distance))
            }
        }
    }

    fun isCollidingBlock(position: Position, block: Vector3ic): Boolean {
        return isBetween(position.x, block.x().toFloat(), (block.x() + 1).toFloat()) &&
                isBetween(position.y, block.y().toFloat(), (block.y() + 1).toFloat()) &&
                isBetween(position.z, block.z().toFloat(), (block.z() + 1).toFloat())
    }
}
