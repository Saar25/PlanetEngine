package org.saar.minecraft.components

import org.joml.Vector3i
import org.joml.Vector3ic
import org.saar.core.common.components.TransformComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.input.mouse.MouseButton
import org.saar.minecraft.Blocks
import org.saar.minecraft.World
import org.saar.minecraft.chunk.ChunkRenderer
import org.saar.minecraft.entity.RayCasting

class PlayerBuildingComponent(
    private val mouse: Mouse,
    private val world: World,
    private val delay: Long,
) : NodeComponent {

    private var lastBuild = System.currentTimeMillis()

    private val directions = arrayOf<Vector3ic>(
        Vector3i(+1, 0, 0),
        Vector3i(-1, 0, 0),
        Vector3i(0, +1, 0),
        Vector3i(0, -1, 0),
        Vector3i(0, 0, +1),
        Vector3i(0, 0, -1)
    )

    private lateinit var transformComponent: TransformComponent
    private lateinit var collisionComponent: CollisionComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
        this.collisionComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val transform = this.transformComponent.transform
        val rayCast = RayCasting.lookingAtFace(transform, this.world, 16)

        if (rayCast == null || !rayCast.block.isCollideable) {
            ChunkRenderer.rayCastedFace.value.set(0, 0, 0, -1)
        } else {
            ChunkRenderer.rayCastedFace.value.set(
                rayCast.x, rayCast.y, rayCast.z, rayCast.direction)

            if (this.lastBuild + this.delay <= System.currentTimeMillis()) {
                if (this.mouse.isButtonDown(MouseButton.PRIMARY)) {
                    this.lastBuild = System.currentTimeMillis()
                    this.world.setBlock(rayCast.x, rayCast.y, rayCast.z, Blocks.AIR)
                }
                if (this.mouse.isButtonDown(MouseButton.SECONDARY)) {
                    this.lastBuild = System.currentTimeMillis()
                    val position = Vector3i(this.directions[rayCast.direction]).add(rayCast.position)
                    if (!this.collisionComponent.isCollidingBlock(position)) {
                        this.world.setBlock(position.x, position.y, position.z, Blocks.STONE)
                    }
                }
            }
        }
    }
}