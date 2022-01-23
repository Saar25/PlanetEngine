package org.saar.minecraft

import org.lwjgl.glfw.GLFW
import org.saar.core.common.components.TransformComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.core.util.Time
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.maths.utils.Vector3
import org.saar.minecraft.entity.HitBoxes
import kotlin.math.floor

class PlayerMovementComponent(
    private val keyboard: Keyboard,
    private val world: World,
    private val speed: Float,
    private val flyMode: Boolean,
) : NodeComponent {

    private val time: Time = Time()

    private var yVelocity: Float = 0f

    private lateinit var transformComponent: TransformComponent

    private fun isOnBlock(world: World): Boolean {
        val position = this.transformComponent.transform.position
        val block = world.getBlock(
            floor(position.x.toDouble()).toInt(),
            floor((position.y - 1.51f).toDouble()).toInt(),
            floor(position.z.toDouble()).toInt())
        return block.isCollideable
    }

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val direction = Vector3.create()
        if (this.keyboard.isKeyPressed('W'.code)) {
            direction.add(0f, 0f, -1f)
        }
        if (this.keyboard.isKeyPressed('A'.code)) {
            direction.add(-1f, 0f, 0f)
        }
        if (this.keyboard.isKeyPressed('S'.code)) {
            direction.add(0f, 0f, +1f)
        }
        if (this.keyboard.isKeyPressed('D'.code)) {
            direction.add(+1f, 0f, 0f)
        }

        direction.rotate(this.transformComponent.transform.rotation.value).y = 0f

        val speed = if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) this.speed * 5 else this.speed

        if (this.flyMode) {
            if (direction.lengthSquared() > 0) {
                direction.normalize(speed)
            }
            if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                direction.add(0f, -this.speed, 0f)
            }
            if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                direction.add(0f, +this.speed, 0f)
            }
            val position = this.transformComponent.transform.position

            val ensured = HitBoxes.player.collideWithWorld(
                this.world, position, direction)

            this.transformComponent.transform.position.add(ensured)

        } else {
            // TODO: MAKE IT WORK, AND SEPARATE NODE COMPONENTS
            if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                if (isOnBlock(world)) {
                    this.yVelocity = .25f
                }

            }
            if (direction.lengthSquared() > 0) {
                direction.normalize(speed)
            }

            val position = this.transformComponent.transform.position

            val ensured = HitBoxes.player.collideWithWorld(
                this.world, position, Vector3.of(direction).add(0f, this.yVelocity, 0f))

            this.transformComponent.transform.position.add(ensured)

            if (isOnBlock(world) || ensured.y == 0f) {
                this.yVelocity = 0f
            }
            this.yVelocity -= .98f * this.time.delta().toSeconds()
        }

        this.time.update()
    }
}