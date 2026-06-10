package org.saar.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.joml.SimplexNoise
import org.joml.Vector3f
import org.joml.Vector3fc
import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.LevelOfDetailComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D
import org.saar.core.common.r3d.Vertex3D
import org.saar.core.light.DirectionalLight
import org.saar.core.mesh.DrawCallMesh
import org.saar.core.mesh.lod.LodMesh
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPipeline
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.deferred.asDeferredRenderNode
import org.saar.core.renderer.deferred.passes.LightRenderPass
import org.saar.core.renderer.onto
import org.saar.core.renderer.renderpass.asRenderNode
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.clear
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.Face
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.polygonmode.PolygonMode
import org.saar.lwjgl.opengl.polygonmode.PolygonModeState
import org.saar.lwjgl.opengl.polygonmode.PolygonModeValue
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector3
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private const val NUM_CONTINENTS = 12
private const val CHUNKS = 20
private const val SUBDIVISIONS = 5
private const val WATER_PROBABILITY = 0.5
private const val PLANET_SCALE = 16f
private const val NOISE_HEIGHT_AMPLITUDE = 0.006f
private const val MOUNTAIN_HEIGHT = 0.02f
private const val HEIGHT_NOISE_FREQ = 5f
private const val TEMP_NOISE_AMPLITUDE = 0.05f
private const val TEMP_NOISE_FREQ = 3f
private const val PLANET_TILT_DEG = 23.5
private const val TEMP_POLAR_THRESHOLD = 0.2f
private const val CAMERA_FOV = 70f
private const val CAMERA_NEAR = 0.1f
private const val CAMERA_FAR = 1000f
private const val CAMERA_MOUSE_SENSITIVITY = -0.3f
private const val CAMERA_KEYBOARD_SPEED = 5f

private class ChunkLodData(
    val combinedIndices: IntArray,
    val lodByteOffsets: LongArray,
    val lodCounts: IntArray,
)

private class ChunkData(
    val verts: Array<Vertex3D>,
    val lods: ChunkLodData,
)

fun main() {
    val window = Window.create("Lwjgl", 1200, 700, true)
    ClearColour.set(0.53f, 0.81f, 0.92f)

    val camera = buildCamera(window.mouse, window.keyboard)

    val chunkDataList = buildIcosahedron()
    val icoInstance = R3D.instance().also {
        it.transform.position.set(0f, 0f, 0f)
        it.transform.scale.set(PLANET_SCALE)
    }
    val continentNodes = chunkDataList.map { chunkData ->
        val icoVao = R3D.meshBuilder(
            arrayOf(icoInstance), chunkData.verts, chunkData.lods.combinedIndices
        ).loadVao()

        val lodMeshes = (0..SUBDIVISIONS).map { lod ->
            val drawCall = InstancedElementsDrawCall(
                RenderMode.TRIANGLES, chunkData.lods.lodCounts[lod],
                DataType.U_INT, chunkData.lods.lodByteOffsets[lod], 1
            )
            DrawCallMesh(icoVao, drawCall)
        }
        val lodMesh = LodMesh(lodMeshes)

        val icoModel = Model3D(lodMesh).also { it.specular = 0f }
        Node3D(icoModel).apply {
            components.add(
                LevelOfDetailComponent(
                    camera,
                    lodMesh.lod,
                    (0..SUBDIVISIONS).map { 8 * it + 24 }.reversed().toIntArray()
                )
            )
        }
    }
    val nodeGroup = DeferredRenderNodeGroup(*continentNodes.toTypedArray())

    val light = DirectionalLight().also {
        it.direction.set(-1f, -1f, -1f)
        it.colour.set(1f, 1f, 1f)
    }

    val prototype = DeferredScreenPrototype()
    val screen = prototype.toScreen(Fbo.create(window.width, window.height))

    val pipeline = RenderPipeline(
        nodeGroup.asDeferredRenderNode().onto(screen),
        LightRenderPass(light).asRenderNode(prototype.buffers).onto(MainScreen)
    )

    val keyboard = window.keyboard
    keyboard.onKeyPress('J').perform {
        PolygonMode.set(
            PolygonModeState(
                face = Face.FRONT_AND_BACK,
                mode = PolygonModeValue.LINE,
            )
        )
    }
    keyboard.onKeyPress('K').perform {
        PolygonMode.set(
            PolygonModeState(
                face = Face.FRONT_AND_BACK,
                mode = PolygonModeValue.FILL,
            )
        )
    }
    while (window.isOpen && !keyboard.allKeysPressed('Q'.code, GLFW.GLFW_KEY_LEFT_ALT)) {
        camera.update()
        nodeGroup.update()

        screen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        pipeline.render(RenderContext(camera))

        window.swapBuffers()
        window.pollEvents()
    }

    screen.delete()
    pipeline.delete()
    window.destroy()
}

private fun buildCamera(mouse: Mouse, keyboard: Keyboard): Camera {
    val projection = ScreenPerspectiveProjection(CAMERA_FOV, CAMERA_NEAR, CAMERA_FAR)

    val components = NodeComponentGroup(
        MouseDragRotationComponent(mouse, CAMERA_MOUSE_SENSITIVITY),
        KeyboardMovementComponent(keyboard, Vector3.of(CAMERA_KEYBOARD_SPEED))
    )

    val camera = Camera(projection, components)

    camera.transform.position.set(0f, PLANET_SCALE + 10f, 10f)
    camera.transform.lookAt(Position.of(0f, 0f, 0f))
    return camera
}

private fun buildIcosahedron(): List<ChunkData> {
    val phi = ((1.0 + sqrt(5.0)) / 2.0).toFloat()

    var verts: List<Vector3fc> = listOf(
        Vector3.of(-1f, phi, 0f), Vector3.of(1f, phi, 0f),
        Vector3.of(-1f, -phi, 0f), Vector3.of(1f, -phi, 0f),
        Vector3.of(0f, -1f, phi), Vector3.of(0f, 1f, phi),
        Vector3.of(0f, -1f, -phi), Vector3.of(0f, 1f, -phi),
        Vector3.of(phi, 0f, -1f), Vector3.of(phi, 0f, 1f),
        Vector3.of(-phi, 0f, -1f), Vector3.of(-phi, 0f, 1f),
    ).map { Vector3.normalize(it) }

    var faces = listOf(
        listOf(0, 11, 5), listOf(0, 5, 1), listOf(0, 1, 7),
        listOf(0, 7, 10), listOf(0, 10, 11), listOf(1, 5, 9),
        listOf(5, 11, 4), listOf(11, 10, 2), listOf(10, 7, 6),
        listOf(7, 1, 8), listOf(3, 9, 4), listOf(3, 4, 2),
        listOf(3, 2, 6), listOf(3, 6, 8), listOf(3, 8, 9),
        listOf(4, 9, 5), listOf(2, 4, 11), listOf(6, 2, 10),
        listOf(8, 6, 7), listOf(9, 8, 1),
    )

    val vertsAtLevel = mutableListOf(verts)
    val facesAtLevel = mutableListOf(faces)
    val faceOriginAtLevel = mutableListOf(faces.indices.toList())

    repeat(SUBDIVISIONS) {
        val (v, f) = subdivide(verts, faces)
        verts = v
        faces = f
        vertsAtLevel.add(verts)
        facesAtLevel.add(faces)
        faceOriginAtLevel.add(faceOriginAtLevel.last().flatMap { listOf(it, it, it, it) })
    }

    val finalVerts = vertsAtLevel.last()
    val finalFaces = facesAtLevel.last()
    val finalFaceOrigin = faceOriginAtLevel.last()

    val vertFacesFinal = finalVerts.indices.map { v ->
        finalFaces.mapIndexedNotNull { fi, (i0, i1, i2) ->
            if (v == i0 || v == i1 || v == i2) fi else null
        }
    }

    val chunkForVertex = finalVerts.indices.map { v ->
        vertFacesFinal[v].groupBy { finalFaceOrigin[it] }.maxByOrNull { it.value.size }?.key ?: 0
    }

    val vertAdj = mutableMapOf<Int, MutableSet<Int>>()
    for ((i0, i1, i2) in finalFaces) {
        vertAdj.getOrPut(i0) { mutableSetOf() }.add(i1)
        vertAdj.getOrPut(i0) { mutableSetOf() }.add(i2)
        vertAdj.getOrPut(i1) { mutableSetOf() }.add(i0)
        vertAdj.getOrPut(i1) { mutableSetOf() }.add(i2)
        vertAdj.getOrPut(i2) { mutableSetOf() }.add(i0)
        vertAdj.getOrPut(i2) { mutableSetOf() }.add(i1)
    }

    val numVerts = finalVerts.size
    val continent = IntArray(numVerts) { -1 }
    val seeds = (0 until numVerts).shuffled().take(NUM_CONTINENTS)
    seeds.forEachIndexed { i, idx -> continent[idx] = i }

    val queue = mutableListOf<Int>()
    seeds.forEach { queue.add(it) }

    while (queue.isNotEmpty()) {
        val idx = queue.removeAt(queue.indices.random())
        val cId = continent[idx]
        val neighbors = vertAdj[idx] ?: continue
        for (n in neighbors.shuffled()) {
            if (continent[n] != -1) continue
            continent[n] = cId
            queue.add(n)
        }
    }

    val isWater = (0 until NUM_CONTINENTS).map { Math.random() < WATER_PROBABILITY }
    val axisTilt = Math.toRadians(PLANET_TILT_DEG)
    val axis = Vector3.of(
        sin(axisTilt).toFloat(),
        cos(axisTilt).toFloat(),
        0f,
    )

    val plateCentroids = Array(NUM_CONTINENTS) { Vector3.create() }
    val plateCounts = IntArray(NUM_CONTINENTS)
    for (v in finalVerts.indices) {
        val cId = continent[v]
        plateCentroids[cId].add(finalVerts[v])
        plateCounts[cId]++
    }
    for (cId in 0 until NUM_CONTINENTS) {
        plateCentroids[cId].div(plateCounts[cId].toFloat())
        plateCentroids[cId].normalize()
    }

    val plateDirections = Array(NUM_CONTINENTS) { cId ->
        val rand = Vector3.of(
            (Math.random() * 2 - 1).toFloat(),
            (Math.random() * 2 - 1).toFloat(),
            (Math.random() * 2 - 1).toFloat()
        )
        val n = plateCentroids[cId]
        val tangent = Vector3.sub(rand, Vector3.mul(n, n.dot(rand)))
        Vector3.normalize(tangent)
    }

    data class PlatePair(val pA: Int, val pB: Int, val cA: Vector3fc, val cB: Vector3fc)

    val chunkAllVerts = Array(CHUNKS) { mutableListOf<Vertex3D>() }
    val chunkAllIndices = Array(CHUNKS) { mutableListOf<Int>() }
    val chunkLodCounts = Array(CHUNKS) { mutableListOf<Int>() }

    for (level in 0..SUBDIVISIONS) {
        val levelVerts = vertsAtLevel[level]
        val levelFaces = facesAtLevel[level]

        val levelCentroids = levelFaces.map { (i0, i1, i2) ->
            Vector3.normalize(
                Vector3.div(
                    Vector3.add(levelVerts[i0], Vector3.add(levelVerts[i1], levelVerts[i2])), 3f
                )
            )
        }
        val levelVertFaces = levelVerts.indices.map { v ->
            levelFaces.mapIndexedNotNull { fi, (i0, i1, i2) ->
                if (v == i0 || v == i1 || v == i2) fi else null
            }
        }

        val levelConvergentFace = BooleanArray(levelFaces.size) { false }
        val levelLandWaterFace = BooleanArray(levelFaces.size) { false }
        runBlocking {
            levelFaces.indices.map { fi ->
                async(Dispatchers.Default) {
                    val (i0, i1, i2) = levelFaces[fi]
                    val c0 = continent[i0];
                    val c1 = continent[i1];
                    val c2 = continent[i2]
                    val w0 = isWater[c0];
                    val w1 = isWater[c1];
                    val w2 = isWater[c2]
                    val diff = c0 != c1 || c1 != c2
                    levelLandWaterFace[fi] = diff && (w0 != w1 || w1 != w2)
                    val ll = diff && !w0 && !w1 && !w2
                    if (ll) {
                        val nrm = levelCentroids[fi]
                        val pp = when {
                            c0 == c1 -> PlatePair(
                                c0, c2,
                                Vector3.div(Vector3.add(levelVerts[i0], levelVerts[i1]), 2f), levelVerts[i2]
                            )

                            c1 == c2 -> PlatePair(
                                c0, c1,
                                levelVerts[i0], Vector3.div(Vector3.add(levelVerts[i1], levelVerts[i2]), 2f)
                            )

                            c2 == c0 -> PlatePair(
                                c0, c1,
                                Vector3.div(Vector3.add(levelVerts[i2], levelVerts[i0]), 2f), levelVerts[i1]
                            )

                            else -> null
                        }
                        if (pp != null) {
                            val ab = Vector3.sub(pp.cB, pp.cA)
                            val ab_t = Vector3.sub(ab, Vector3.mul(nrm, nrm.dot(ab)))
                            if (!(ab_t.x() == 0f && ab_t.y() == 0f && ab_t.z() == 0f)) {
                                val abDir = Vector3.normalize(ab_t)
                                val vDirA = plateDirections[pp.pA]
                                val vDirB = plateDirections[pp.pB]
                                val vA_t = Vector3.sub(vDirA, Vector3.mul(nrm, nrm.dot(vDirA)))
                                val vB_t = Vector3.sub(vDirB, Vector3.mul(nrm, nrm.dot(vDirB)))
                                levelConvergentFace[fi] = vA_t.dot(abDir) > 0f && vB_t.dot(abDir) < 0f
                            }
                        }
                    }

                }
            }.awaitAll()
        }

        val levelDisplacedVerts = levelVerts.indices.map { v ->
            val pos = levelVerts[v]
            val cId = continent[v]
            if (!isWater[cId]) {
                val adj = levelVertFaces[v]
                val isMountain = adj.any { levelConvergentFace[it] }
                val n = SimplexNoise.noise(
                    pos.x() * HEIGHT_NOISE_FREQ, pos.y() * HEIGHT_NOISE_FREQ, pos.z() * HEIGHT_NOISE_FREQ
                )
                val h = n * NOISE_HEIGHT_AMPLITUDE + (if (isMountain) MOUNTAIN_HEIGHT else 0f)
                Vector3.add(pos, Vector3.mul(pos, h))
            } else {
                Vector3.of(pos.x(), pos.y(), pos.z())
            }
        }

        val chunkVerts = Array(CHUNKS) { mutableListOf<Vertex3D>() }
        val chunkIdx = Array(CHUNKS) { mutableListOf<Int>() }
        for (fi in levelFaces.indices) {
            val (i0, i1, i2) = levelFaces[fi]
            val p0 = levelDisplacedVerts[i0]
            val p1 = levelDisplacedVerts[i1]
            val p2 = levelDisplacedVerts[i2]

            val fn = faceNormal(p0, p1, p2)

            val centroid = levelCentroids[fi]
            val cosLat = kotlin.math.abs(centroid.dot(axis))
            val noiseTemp = SimplexNoise.noise(
                centroid.x() * TEMP_NOISE_FREQ, centroid.y() * TEMP_NOISE_FREQ,
                centroid.z() * TEMP_NOISE_FREQ
            )
            val temp = (1f - cosLat + noiseTemp * TEMP_NOISE_AMPLITUDE).coerceIn(0f, 1f)
            val isMountain = levelConvergentFace[fi]
            val isCoast = levelLandWaterFace[fi]
            val col = when {
                isMountain -> stoneColor(temp)
                isCoast -> sandColor(temp)
                isWater[continent[i0]] || isWater[continent[i1]] || isWater[continent[i2]] -> waterColor(temp)
                else -> landColor(temp)
            }

            val ci = chunkForVertex[i0]

            val base = chunkVerts[ci].size
            chunkVerts[ci].add(R3D.vertex(p0, fn, col))
            chunkVerts[ci].add(R3D.vertex(p1, fn, col))
            chunkVerts[ci].add(R3D.vertex(p2, fn, col))
            chunkIdx[ci].add(base)
            chunkIdx[ci].add(base + 1)
            chunkIdx[ci].add(base + 2)
        }

        for (ci in 0 until CHUNKS) {
            chunkLodCounts[ci].add(chunkIdx[ci].size)
            val vertBase = chunkAllVerts[ci].size
            chunkAllVerts[ci].addAll(chunkVerts[ci])
            for (i in chunkIdx[ci]) chunkAllIndices[ci].add(i + vertBase)
        }
    }

    return (0 until CHUNKS).map { ci ->
        val counts = chunkLodCounts[ci]
        val offsets = mutableListOf<Long>()
        var cumulative = 0L
        for (count in counts) {
            offsets.add(cumulative * 4L)
            cumulative += count.toLong()
        }
        ChunkData(
            chunkAllVerts[ci].toTypedArray(),
            ChunkLodData(
                chunkAllIndices[ci].toIntArray(),
                offsets.toLongArray(),
                counts.toIntArray(),
            ),
        )
    }
}

private fun faceNormal(v0: Vector3fc, v1: Vector3fc, v2: Vector3fc): Vector3f {
    val e1 = Vector3.sub(v1, v0)
    val e2 = Vector3.sub(v2, v0)
    val n = Vector3.normalize(Vector3.cross(e1, e2))
    return if (n.dot(v0) < 0) Vector3.mul(n, -1f) else n
}

private fun lerp(a: Vector3fc, b: Vector3fc, t: Float): Vector3f {
    return Vector3.of(
        a.x() + (b.x() - a.x()) * t,
        a.y() + (b.y() - a.y()) * t,
        a.z() + (b.z() - a.z()) * t,
    )
}

private fun waterColor(temp: Float): Vector3f {
    val t = temp.coerceIn(0f, 1f)
    return if (t < TEMP_POLAR_THRESHOLD) lerp(
        Vector3.of(0.85f, 0.9f, 1.0f),
        Vector3.of(0f, 0.15f, 0.4f),
        t / TEMP_POLAR_THRESHOLD
    )
    else lerp(
        Vector3.of(0f, 0.15f, 0.4f),
        Vector3.of(0f, 0.5f, 0.7f),
        (t - TEMP_POLAR_THRESHOLD) / (1f - TEMP_POLAR_THRESHOLD)
    )
}

private fun landColor(temp: Float): Vector3f {
    val t = temp.coerceIn(0f, 1f)
    return if (t < TEMP_POLAR_THRESHOLD) lerp(
        Vector3.of(0.9f, 0.9f, 1.0f),
        Vector3.of(0.4f, 0.5f, 0.3f),
        t / TEMP_POLAR_THRESHOLD
    )
    else lerp(
        Vector3.of(0.4f, 0.5f, 0.3f),
        Vector3.of(0.05f, 0.45f, 0.08f),
        (t - TEMP_POLAR_THRESHOLD) / (1f - TEMP_POLAR_THRESHOLD)
    )
}

private fun stoneColor(temp: Float): Vector3f =
    lerp(Vector3.of(0.5f, 0.45f, 0.4f), Vector3.of(0.35f, 0.3f, 0.25f), temp.coerceIn(0f, 1f))

private fun sandColor(temp: Float): Vector3f =
    lerp(Vector3.of(0.6f, 0.55f, 0.4f), Vector3.of(0.85f, 0.8f, 0.6f), temp.coerceIn(0f, 1f))

private fun subdivide(vertices: List<Vector3fc>, faces: List<List<Int>>): Pair<List<Vector3fc>, List<List<Int>>> {
    val newVertices = vertices.toMutableList()
    val edgeMap = mutableMapOf<Long, Int>()

    fun getMidpoint(i1: Int, i2: Int): Int {
        val key = if (i1 < i2) (i1.toLong() shl 32) or i2.toLong()
        else (i2.toLong() shl 32) or i1.toLong()
        return edgeMap.getOrPut(key) {
            val mid = Vector3.normalize(Vector3.add(vertices[i1], vertices[i2]))
            newVertices.add(mid)
            newVertices.size - 1
        }
    }

    val newFaces = mutableListOf<List<Int>>()
    for ((i0, i1, i2) in faces) {
        val m01 = getMidpoint(i0, i1)
        val m12 = getMidpoint(i1, i2)
        val m20 = getMidpoint(i2, i0)

        newFaces.add(listOf(i0, m01, m20))
        newFaces.add(listOf(m01, i1, m12))
        newFaces.add(listOf(m20, m12, i2))
        newFaces.add(listOf(m01, m12, m20))
    }

    return Pair(newVertices, newFaces)
}
