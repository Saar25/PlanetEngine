package org.saar.minecraft.chunk

import org.saar.minecraft.Chunk
import org.saar.minecraft.EmptyChunk
import java.util.*
import kotlin.math.max

class ChunkLights(private val chunk: Chunk) {

    private val lights = ByteArray(16 * 16 * 256) { 0xFF.toByte() }

    private fun calculateLight(x: Int, y: Int, z: Int): Int {
        val block = this.chunk.getBlock(x, y, z)
        val p = block.lightPropagation
        if (block.isTransparent) {
            var max = 0
            max = max(max, this.chunk.getLight(x, y + 1, z) - p.down)
            max = max(max, this.chunk.getLight(x, y - 1, z) - p.side)
            max = max(max, this.chunk.getLight(x - 1, y, z) - p.side)
            max = max(max, this.chunk.getLight(x + 1, y, z) - p.side)
            max = max(max, this.chunk.getLight(x, y, z - 1) - p.side)
            max = max(max, this.chunk.getLight(x, y, z + 1) - p.side)
            return max
        }
        return 0
    }

    fun recalculateLight() {
        this.lights.fill(0.toByte())

        val spreadBfs = LinkedList<LightNode>()
        for (x in 0..15) {
            for (z in 0..15) {
                if (this.chunk.getBlock(x, 0xFF, z).isTransparent) {
                    spreadBfs.add(LightNode(x, 0xFF, z, 0xFF))
                }
            }
        }

        spreadLight(spreadBfs)
    }

    fun updateLight(x: Int, y: Int, z: Int) {
        val light = calculateLight(x, y, z)
        updateLight(x, y, z, light)
    }

    private fun updateLight(x: Int, y: Int, z: Int, light: Int) {
        val spreadBfs = LinkedList<LightNode>()
        val removeBfs = LinkedList<LightNode>()

        if (this.chunk.getBlock(x, y, z).isTransparent) {
            spreadBfs.add(LightNode(x, y, z, light))
            this.chunk.setLight(x, y, z, light.toByte())
        } else {
            removeBfs.add(LightNode(x, y, z, getLight(x, y, z)))
            this.chunk.setLight(x, y, z, 0.toByte())
        }

        removeLight(spreadBfs, removeBfs)
        spreadLight(spreadBfs)
    }


    private fun removeLight(addBfs: Queue<LightNode>, remBfs: Queue<LightNode>) {
        while (!remBfs.isEmpty()) {
            val n = remBfs.poll()
            removeLightDown(addBfs, remBfs, n.x, n.y - 1, n.z, n)
            removeLightSide(addBfs, remBfs, n.x, n.y + 1, n.z, n)
            removeLightSide(addBfs, remBfs, n.x + 1, n.y, n.z, n)
            removeLightSide(addBfs, remBfs, n.x - 1, n.y, n.z, n)
            removeLightSide(addBfs, remBfs, n.x, n.y, n.z + 1, n)
            removeLightSide(addBfs, remBfs, n.x, n.y, n.z - 1, n)
        }
    }

    private fun removeLightDown(
        addBfs: Queue<LightNode>,
        remBfs: Queue<LightNode>,
        x: Int,
        y: Int,
        z: Int,
        n: LightNode
    ) {
        val block = this.chunk.getBlock(x, y, z)
        val p = block.lightPropagation
        val spread = max(n.level - p.down, 0)
        removeLight(addBfs, remBfs, x, y, z, spread)
    }

    private fun removeLightSide(
        addBfs: Queue<LightNode>,
        remBfs: Queue<LightNode>,
        x: Int,
        y: Int,
        z: Int,
        n: LightNode
    ) {
        val block = this.chunk.getBlock(x, y, z)
        val p = block.lightPropagation
        val spread = max(n.level - p.side, 0)
        removeLight(addBfs, remBfs, x, y, z, spread)
    }

    private fun spreadLight(spreadBfs: Queue<LightNode>) {
        while (!spreadBfs.isEmpty()) {
            val n = spreadBfs.poll()
            spreadLightDown(spreadBfs, n.x, n.y - 1, n.z, n)
            spreadLightSide(spreadBfs, n.x, n.y + 1, n.z, n)
            spreadLightSide(spreadBfs, n.x + 1, n.y, n.z, n)
            spreadLightSide(spreadBfs, n.x - 1, n.y, n.z, n)
            spreadLightSide(spreadBfs, n.x, n.y, n.z + 1, n)
            spreadLightSide(spreadBfs, n.x, n.y, n.z - 1, n)
        }
    }

    private fun spreadLightDown(addBfs: Queue<LightNode>, x: Int, y: Int, z: Int, n: LightNode) {
        val block = this.chunk.getBlock(x, y, z)
        val p = block.lightPropagation
        val spread = max(n.level - p.down, 0)
        spreadLight(addBfs, x, y, z, spread)
    }

    private fun spreadLightSide(addBfs: Queue<LightNode>, x: Int, y: Int, z: Int, n: LightNode) {
        val block = this.chunk.getBlock(x, y, z)
        val p = block.lightPropagation
        val spread = max(n.level - p.side, 0)
        spreadLight(addBfs, x, y, z, spread)
    }

    private fun spreadLight(addBfs: Queue<LightNode>, x: Int, y: Int, z: Int, spread: Int) {
        if (y !in 0..255 || this.chunk.getRelativeChunk(x, z) === EmptyChunk) return
        if (spread < 0) return

        val light = this.chunk.getLight(x, y, z)
        val block = this.chunk.getBlock(x, y, z)
        if (block.isTransparent && light < spread) {
            this.chunk.setLight(x, y, z, spread.toByte())

            addBfs.add(LightNode(x, y, z, spread))
        }
    }

    private fun removeLight(addBfs: Queue<LightNode>, remBfs: Queue<LightNode>, x: Int, y: Int, z: Int, spread: Int) {
        if (y !in 0..255 || this.chunk.getRelativeChunk(x, z) === EmptyChunk) return

        val light = this.chunk.getLight(x, y, z)
        if (light != 0 && light <= spread) {
            this.chunk.setLight(x, y, z, 0.toByte())

            remBfs.add(LightNode(x, y, z, spread))
        } else if (light > spread) {
            addBfs.add(LightNode(x, y, z, light))
        }
    }

    fun getLight(x: Int, y: Int, z: Int): Int {
        val index: Int = index(x, y, z)
        return getLight(index)
    }

    private fun getLight(index: Int): Int {
        return this.lights[index].toInt() and 0xFF
    }

    fun setLight(x: Int, y: Int, z: Int, level: Byte) {
        val index: Int = index(x, y, z)
        this.lights[index] = level
    }

    private class LightNode(val x: Int, val y: Int, val z: Int, val level: Int) {
        override fun toString(): String {
            return "LightNode{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", level=" + level +
                    '}'
        }
    }

    companion object {
        private fun index(x: Int, y: Int, z: Int): Int {
            return ((x and 0xF) shl 12) or ((z and 0xF) shl 8) or (y and 0xFF)
        }
    }
}
