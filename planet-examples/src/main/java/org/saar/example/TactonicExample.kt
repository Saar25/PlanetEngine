package org.saar.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.joml.SimplexNoise
import org.joml.Vector2i
import org.joml.Vector3f
import org.joml.Vector3fc
import org.lwjgl.glfw.GLFW
import org.saar.core.camera.Camera
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.R3D.instance
import org.saar.core.common.r3d.R3D.mesh
import org.saar.core.common.r3d.R3D.vertex
import org.saar.core.common.r3d.Vertex3D
import org.saar.core.common.terrain.colour.ColourGenerator
import org.saar.core.common.terrain.colour.NormalColour
import org.saar.core.common.terrain.colour.NormalColourGenerator
import org.saar.core.common.terrain.height.HeightGenerator
import org.saar.core.common.terrain.height.NoiseHeightGenerator
import org.saar.core.common.terrain.lowpoly.LowPolyTerrainFactory
import org.saar.core.common.terrain.lowpoly.LowPolyWorld
import org.saar.core.common.terrain.mesh.DiamondMeshGenerator
import org.saar.core.light.DirectionalLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.renderer.deferred.passes.LightRenderPass
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.maths.noise.LayeredNoise2f
import org.saar.maths.noise.MultipliedNoise2f
import org.saar.maths.noise.SpreadNoise2f
import org.saar.maths.transform.Position
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3

fun main() {
    val window = Window.create("Lwjgl", 1200, 700, true)
    ClearColour.set(0.53f, 0.81f, 0.92f)

    val camera = buildCamera(window.mouse, window.keyboard)

    val world = buildWorld()
    world.createTerrain(Vector2i(0))

    val continentMeshes = buildIcosahedron(numContinents = 8)
    val continentNodes = continentMeshes.map { (verts, idx) ->
        val icoInstance = instance().also {
            it.transform.position.set(0f, 5f, 0f)
            it.transform.scale.set(8f, 8f, 8f)
        }
        val icoMesh = mesh(arrayOf(icoInstance), verts, idx)
        val icoModel = Model3D(icoMesh).also { it.specular = 0f }
        Node3D(icoModel)
    }

    val light = DirectionalLight().also {
        it.direction.set(-1f, -1f, -1f)
        it.colour.set(1f, 1f, 1f)
    }

    val renderingPipeline = DeferredRenderingPipeline(
        DeferredGeometryPass(*continentNodes.toTypedArray()), LightRenderPass(light))
    val renderingPath = DeferredRenderingPath(camera, renderingPipeline)

    val keyboard = window.keyboard
    while (window.isOpen && !keyboard.allKeysPressed('Q'.code, GLFW.GLFW_KEY_LEFT_ALT)) {
        camera.update()

        renderingPath.render().toMainScreen()

        window.swapBuffers()
        window.pollEvents()
    }

    window.destroy()
}

private fun buildCamera(mouse: Mouse, keyboard: Keyboard): Camera {
    val projection = ScreenPerspectiveProjection(70f, .1f, 1000f)

    val components = NodeComponentGroup(
        MouseDragRotationComponent(mouse, -.3f),
        KeyboardMovementComponent(keyboard, Vector3.of(5f)))

    val camera = Camera(projection, components)

    camera.transform.position.set(0f, 10f, 10f)
    camera.transform.lookAt(Position.of(0f, 0f, 0f))
    return camera
}

private fun buildWorld(): LowPolyWorld {
    val heightGenerator: HeightGenerator = NoiseHeightGenerator(
        MultipliedNoise2f(18, SpreadNoise2f(8,
            LayeredNoise2f({ x: Float, y: Float -> SimplexNoise.noise(x, y) }, 5)))
    )
    val colourGenerator: ColourGenerator = NormalColourGenerator(Vector3.upward(),
        NormalColour(0.90f, Vector3.of(.41f, .41f, .41f)),
        NormalColour(1.0f, Vector3.of(.07f, .52f, .06f)))
    val terrainFactory = LowPolyTerrainFactory(
        DiamondMeshGenerator(64), heightGenerator,
        colourGenerator, Vector2.of(64f, 64f)
    )
    return LowPolyWorld(terrainFactory)
}

private fun buildIcosahedron(numContinents: Int = 8): List<Pair<Array<Vertex3D>, IntArray>> {
    val phi = ((1.0 + Math.sqrt(5.0)) / 2.0).toFloat()

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

    repeat(5) {
        val (v, f) = subdivide(verts, faces)
        verts = v
        faces = f
    }

    val centroids = faces.map { (i0, i1, i2) ->
        Vector3.normalize(Vector3.div(
            Vector3.add(verts[i0], Vector3.add(verts[i1], verts[i2])), 3f))
    }
    val offsetCentroids = centroids.toMutableList()

    val vertFaces = verts.indices.map { v ->
        faces.mapIndexedNotNull { fi, (i0, i1, i2) ->
            if (v == i0 || v == i1 || v == i2) fi else null
        }
    }

    val vertAdj = mutableMapOf<Int, MutableSet<Int>>()
    for ((i0, i1, i2) in faces) {
        vertAdj.getOrPut(i0) { mutableSetOf() }.add(i1)
        vertAdj.getOrPut(i0) { mutableSetOf() }.add(i2)
        vertAdj.getOrPut(i1) { mutableSetOf() }.add(i0)
        vertAdj.getOrPut(i1) { mutableSetOf() }.add(i2)
        vertAdj.getOrPut(i2) { mutableSetOf() }.add(i0)
        vertAdj.getOrPut(i2) { mutableSetOf() }.add(i1)
    }

    val numFaces = verts.size
    val continent = IntArray(numFaces) { -1 }
    val seeds = (0 until numFaces).shuffled().take(numContinents)
    seeds.forEachIndexed { i, idx -> continent[idx] = i }

    val queue = mutableListOf<Int>()
    seeds.forEach { queue.add(it) }

    while (queue.isNotEmpty()) {
        val idx = queue.removeAt((0 until queue.size).random())
        val cId = continent[idx]
        val neighbors = vertAdj[idx] ?: continue
        for (n in neighbors.shuffled()) {
            if (continent[n] != -1) continue
            continent[n] = cId
            queue.add(n)
        }
    }

    val isWater = (0 until numContinents).map { Math.random() < 0.5 }
    val axisTilt = Math.toRadians(23.5)
    val axis = Vector3.of(
        Math.sin(axisTilt).toFloat(),
        Math.cos(axisTilt).toFloat(),
        0f,
    )

    val boundaryFace = BooleanArray(faces.size) { false }
    val landWaterFace = BooleanArray(faces.size) { false }
    for (fi in faces.indices) {
        val (i0, i1, i2) = faces[fi]
        val diff = continent[i0] != continent[i1] || continent[i1] != continent[i2] || continent[i2] != continent[i0]
        boundaryFace[fi] = diff
        landWaterFace[fi] = diff && (isWater[continent[i0]] != isWater[continent[i1]] ||
                isWater[continent[i1]] != isWater[continent[i2]] ||
                isWater[continent[i2]] != isWater[continent[i0]])
    }
    val landWaterDualFace = verts.indices.map { v -> vertFaces[v].any { landWaterFace[it] } }

    val vertNormals = computeVertexNormals(verts, faces)

    data class DualFaceData(
        val continentId: Int,
        val centroids: List<Vector3fc>,
        val normal: Vector3f,
        val color: Vector3f,
    )

    val faceData = runBlocking {
        verts.indices.map { v ->
            async(Dispatchers.Default) {
                val adj = vertFaces[v]
                if (adj.size < 3) return@async null
                val cents = adj.map { offsetCentroids[it] }
                val n = vertNormals[v]
                val t = tangent(n)
                val b = Vector3.cross(n, t)
                val order = cents.mapIndexed { i, c ->
                    val d = Vector3.sub(c, verts[v])
                    i to kotlin.math.atan2(d.dot(b).toDouble(), d.dot(t).toDouble())
                }.sortedBy { it.second }.map { it.first }
                val sorted = order.map { cents[it] }
                val fn = faceNormal(sorted[0], sorted[1], sorted[2])
                val pos = verts[v]
                val cosLat = kotlin.math.abs(pos.dot(axis))
                val temp = 1f - cosLat
                val col = if (landWaterDualFace[v]) sandColor(temp)
                else if (isWater[continent[v]]) waterColor(temp)
                else landColor(temp)
                DualFaceData(continent[v], sorted, fn, col)
            }
        }.awaitAll().filterNotNull()
    }

    val chunkVerts = Array(numContinents) { mutableListOf<Vertex3D>() }
    val chunkIdx = Array(numContinents) { mutableListOf<Int>() }

    for (data in faceData) {
        val cId = data.continentId
        val base = chunkVerts[cId].size
        for (p in data.centroids) chunkVerts[cId].add(vertex(p, data.normal, data.color))
        for (i in 1 until data.centroids.size - 1) {
            chunkIdx[cId].add(base)
            chunkIdx[cId].add(base + i)
            chunkIdx[cId].add(base + i + 1)
        }
    }

    return chunkVerts.zip(chunkIdx) { verts, idx ->
        Pair(verts.toTypedArray(), idx.toIntArray())
    }
}

private fun computeVertexNormals(
    verts: List<Vector3fc>, faces: List<List<Int>>
): List<Vector3f> {
    val faceNormals = faces.map { (i0, i1, i2) ->
        val e1 = Vector3.sub(verts[i1], verts[i0])
        val e2 = Vector3.sub(verts[i2], verts[i0])
        Vector3.normalize(Vector3.cross(e1, e2))
    }
    return verts.indices.map { i ->
        val sum = Vector3.create()
        faceNormals.filterIndexed { fi, _ -> i in faces[fi] }.forEach { sum.add(it) }
        sum.normalize()
        sum
    }
}

private fun tangent(n: Vector3fc): Vector3f {
    val up = Vector3.of(0f, 1f, 0f)
    val ref = if (kotlin.math.abs(n.dot(up)) > 0.9f) Vector3.of(1f, 0f, 0f) else up
    return Vector3.normalize(Vector3.cross(n, ref))
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

private fun waterColor(temp: Float): Vector3f =
    lerp(Vector3.of(0.1f, 0.15f, 0.4f), Vector3.of(0f, 0.5f, 0.7f), temp)

private fun landColor(temp: Float): Vector3f =
    lerp(Vector3.of(0.9f, 0.9f, 1.0f), Vector3.of(0.1f, 0.5f, 0.1f), temp)

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