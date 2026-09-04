package org.saar.minecraft.entity

import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.Vector3ic
import org.saar.maths.transform.Position
import org.saar.maths.transform.ReadonlyPosition
import org.saar.maths.utils.Vector3.of
import org.saar.minecraft.Blocks
import org.saar.minecraft.World
import kotlin.math.floor

class HitBox(private val vertices: Array<Vector3fc>) {

    fun collideWithWorld(world: World, cameraPosition: ReadonlyPosition, direction: Vector3fc): Vector3f {
        val ensured = of(direction)
        for (vertex in this.vertices) {
            val vertexPosition = Position.create()
            vertexPosition.set(cameraPosition)
            vertexPosition.add(vertex)

            Collision.ensureDirection(world, vertexPosition, ensured)
        }
        return ensured
    }

    fun isCollidingBlock(position: Position, block: Vector3ic): Boolean {
        for (vertex in this.vertices) {
            val vertexPosition = Position.create()
            vertexPosition.set(position)
            vertexPosition.add(vertex)

            if (Collision.isCollidingBlock(vertexPosition, block)) {
                return true
            }
        }

        return false
    }

    fun isInsideWater(world: World, position: Position): Boolean {
        for (vertex in this.vertices) {
            val vertexPosition = Position.create()
            vertexPosition.set(position)
            vertexPosition.add(vertex)

            if (world.getBlock(vertexPosition) === Blocks.WATER) {
                return true
            }
        }

        return false
    }

    fun isOnBlock(world: World, position: Position): Boolean {
        for (vertex in this.vertices) {
            val block = world.getBlock(
                floor((vertex.x() + position.x).toDouble()).toInt(),
                floor((vertex.y() + position.y - .1f).toDouble()).toInt(),
                floor((vertex.z() + position.z).toDouble()).toInt()
            )

            if (block.isCollideable) {
                return true
            }
        }
        return false
    }

    companion object {
        fun build(radius: Float, height: Float): HitBox {
            val vertices: MutableList<Vector3fc> = ArrayList(height.toInt() * 4 + 4)
            var i = 0
            while (i < height) {
                addVertices(vertices, radius, i.toFloat())
                i++
            }
            if (height != height.toInt().toFloat()) {
                addVertices(vertices, radius, height)
            }
            return HitBox(vertices.toTypedArray())
        }

        private fun addVertices(vertices: MutableList<Vector3fc>, radius: Float, height: Float) {
            vertices.add(of(+radius, radius - height, +radius))
            vertices.add(of(+radius, radius - height, -radius))
            vertices.add(of(-radius, radius - height, -radius))
            vertices.add(of(-radius, radius - height, +radius))
        }
    }
}
