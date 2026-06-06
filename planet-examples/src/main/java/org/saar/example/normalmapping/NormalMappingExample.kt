package org.saar.example.normalmapping

import org.jproperty.ChangeEvent
import org.saar.core.camera.Camera
import org.saar.core.camera.Projection
import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.camera.projection.ScreenPerspectiveProjection
import org.saar.core.camera.projection.SimpleOrthographicProjection
import org.saar.core.common.components.KeyboardMovementComponent
import org.saar.core.common.components.KeyboardMovementScrollVelocityComponent
import org.saar.core.common.components.MouseDragRotationComponent
import org.saar.core.common.normalmap.NormalMapped
import org.saar.core.common.normalmap.NormalMappedModel
import org.saar.core.common.normalmap.NormalMappedNode
import org.saar.core.common.normalmap.NormalMappedNodeBatch
import org.saar.core.common.obj.Obj
import org.saar.core.common.obj.ObjModel
import org.saar.core.common.obj.ObjNode
import org.saar.core.common.obj.ObjNodeBatch
import org.saar.core.common.r3d.Model3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.NodeBatch3D
import org.saar.core.common.r3d.R3D
import org.saar.core.light.DirectionalLight
import org.saar.core.node.NodeComponentGroup
import org.saar.core.postprocessing.processors.ContrastPostProcessor
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass
import org.saar.core.renderer.shadow.ShadowsQuality
import org.saar.core.renderer.shadow.ShadowsRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNodeGroup
import org.saar.core.renderer.shadow.ShadowsRenderingPath
import org.saar.example.ExamplesUtils
import org.saar.gui.UIChildNode
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UISlider
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.clear.ClearColour.set
import org.saar.lwjgl.opengl.texture.ColourTexture.Companion.of
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.transform.Position.Companion.of
import java.util.*

object NormalMappingExample {
    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        set(0f, .7f, .9f)

        val projection: Projection = ScreenPerspectiveProjection(70f, 1f, 1000f)

        val cameraMovementComponent =
            KeyboardMovementComponent(window.keyboard, 50f, 50f, 50f)
        val components = NodeComponentGroup(cameraMovementComponent,
            KeyboardMovementScrollVelocityComponent(window.mouse),
            MouseDragRotationComponent(window.mouse, -.3f))

        val camera = Camera(projection, components)

        camera.transform.position.set(0f, 0f, 200f)
        camera.transform.lookAt(of(0f, 0f, 0f))

        val normalMappedNodeBatch =
            buildNormalMappedNodeBatch()

        val objNodeBatch = buildObjNodeBatch()

        val nodeBatch3D = buildNodeBatch3D()

        val light = DirectionalLight()
        light.direction.set(-1f, -1f, -1f)
        light.colour.set(1f, 1f, 1f)

        val shadowsRenderNode: ShadowsRenderNode = ShadowsRenderNodeGroup(
            nodeBatch3D, objNodeBatch, nodeBatch3D, normalMappedNodeBatch)
        val shadowProjection: OrthographicProjection = SimpleOrthographicProjection(
            -100f, 100f, -100f, 100f, -100f, 100f)
        val shadowsRenderingPath = ShadowsRenderingPath(
            ShadowsQuality.LOW, shadowProjection, light, shadowsRenderNode)
        val shadowMap = shadowsRenderingPath.render().buffers.depth

        val renderNode = DeferredRenderNodeGroup(
            nodeBatch3D, normalMappedNodeBatch, objNodeBatch)

        val uiDisplay = buildUIDisplay(window, light)

        val renderPassesPipeline = DeferredRenderingPipeline(
            DeferredGeometryPass(renderNode),
            ShadowsRenderPass(shadowsRenderingPath.camera, shadowMap, light),
            ContrastPostProcessor(1.3f),
            FxaaPostProcessor(),
            DeferredGeometryPass(uiDisplay)
        )

        val deferredRenderer = DeferredRenderingPath(camera, renderPassesPipeline)

        var current = System.currentTimeMillis()
        while (window.isOpen && !window.keyboard.isKeyPressed('T'.code)) {
            camera.update()

            shadowsRenderingPath.render()
            deferredRenderer.render().toMainScreen()

            window.swapBuffers()
            window.pollEvents()

            val delta = System.currentTimeMillis() - current

            val fps = 1000f / delta
            print("\r --> " +
                    "Speed: " + String.format("%.2f", cameraMovementComponent.velocity.x()) +
                    ", Fps: " + String.format("%.2f", fps) +
                    ", Delta: " + delta)
            current = System.currentTimeMillis()
        }

        camera.delete()
        shadowsRenderingPath.delete()
        deferredRenderer.delete()
        window.destroy()
    }

    private fun buildUIDisplay(window: Window, light: DirectionalLight): UIDisplay {
        val uiDisplay = UIDisplay(window)

        val uiContainer = UIElement().apply {
            style.padding.set(40)
            style.alignment.value = AlignmentValues.vertical
        }

        uiContainer.add(buildUiSlider(light, 0, "x: "))
        uiContainer.add(buildUiSlider(light, 1, "y: "))
        uiContainer.add(buildUiSlider(light, 2, "z: "))

        uiDisplay.add(uiContainer)

        return uiDisplay
    }

    private fun buildUiSlider(light: DirectionalLight, component: Int, text: String): UIChildNode {
        val uiContainer = UIElement().apply {
            style.margin.set(10, 0, 0, 10)
            style.axisAlignment.value = AxisAlignmentValues.center
            style.fontSize.set(24)
        }

        uiContainer.add(UIText(text))

        val uiSlider = UISlider().apply {
            style.width.set(200)
            style.height.set(30)
            min.set(-1f)
            max.set(1f)
            dynamicValueProperty.addListener { e: ChangeEvent<out Number> ->
                light.direction.setComponent(component, -e.getNewValue().toFloat())
            }
        }

        uiContainer.add(uiSlider)

        return uiContainer
    }

    private fun buildNodeBatch3D(): NodeBatch3D {
        val cubeInstance = R3D.instance()
        cubeInstance.transform.scale.set(10f, 10f, 10f)
        cubeInstance.transform.position.set(0f, 0f, 50f)
        val cubeMesh = R3D.mesh(arrayOf(cubeInstance), ExamplesUtils.cubeVertices, ExamplesUtils.cubeIndices)
        val cubeModel = Model3D(cubeMesh)
        val cube = Node3D(cubeModel)

        return NodeBatch3D(cube)
    }

    private fun buildNormalMappedNodeBatch(): NormalMappedNodeBatch {
        val boulderModel = Objects.requireNonNull<NormalMappedModel>(loadBoulder())
        boulderModel.transform.position.set(0f, 20f, 0f)
        val boulder = NormalMappedNode(boulderModel)

        val barrelModel = Objects.requireNonNull<NormalMappedModel>(loadBarrel())
        barrelModel.transform.position.set(-20f, 20f, 0f)
        val barrel = NormalMappedNode(barrelModel)

        val crateModel = Objects.requireNonNull<NormalMappedModel>(loadCrate())
        crateModel.transform.position.set(+20f, 20f, 0f)
        crateModel.transform.scale.scale(.05f)
        val crate = NormalMappedNode(crateModel)

        return NormalMappedNodeBatch(boulder, barrel, crate)
    }

    private fun buildObjNodeBatch(): ObjNodeBatch {
        val cottageModel = Objects.requireNonNull<ObjModel>(loadCottage())
        val cottage = ObjNode(cottageModel)

        val dragonModel = Objects.requireNonNull<ObjModel>(loadDragon())
        dragonModel.transform.position.set(50f, 0f, 0f)
        val dragon = ObjNode(dragonModel)

        val stallModel = Objects.requireNonNull<ObjModel>(loadStall())
        stallModel.transform.rotation.rotateDegrees(0f, 180f, 0f)
        stallModel.transform.position.set(-50f, 0f, 0f)
        val stall = ObjNode(stallModel)

        return ObjNodeBatch(cottage, dragon, stall)
    }

    private fun loadCottage(): ObjModel? {
        try {
            val mesh = Obj.mesh("/assets/cottage/cottage.obj")
            val texture = Texture2D.of("/assets/cottage/cottage_diffuse.png")
            return ObjModel(mesh, texture)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun loadStall(): ObjModel? {
        try {
            val mesh = Obj.mesh("/assets/stall/stall.model.obj")
            val texture = Texture2D.of("/assets/stall/stall.diffuse.png")
            return ObjModel(mesh, texture)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun loadDragon(): ObjModel? {
        try {
            val mesh = Obj.mesh("/assets/dragon/dragon.model.obj")
            val texture: ReadOnlyTexture = of(255, 215, 0, 255)
            return ObjModel(mesh, texture)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun loadBoulder(): NormalMappedModel? {
        try {
            val mesh = NormalMapped.mesh("/assets/boulder/boulder.model.obj")
            val normalMap: ReadOnlyTexture = Texture2D.of("/assets/boulder/boulder.normal.png")
            val texture: ReadOnlyTexture = Texture2D.of("/assets/boulder/boulder.diffuse.png")
            return NormalMappedModel(mesh, texture, normalMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun loadBarrel(): NormalMappedModel? {
        try {
            val mesh = NormalMapped.mesh("/assets/barrel/barrel.model.obj")
            val normalMap: ReadOnlyTexture = Texture2D.of("/assets/barrel/barrel.normal.png")
            val texture: ReadOnlyTexture = Texture2D.of("/assets/barrel/barrel.diffuse.png")
            return NormalMappedModel(mesh, texture, normalMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun loadCrate(): NormalMappedModel? {
        try {
            val mesh = NormalMapped.mesh("/assets/crate/crate.model.obj")
            val normalMap: ReadOnlyTexture = Texture2D.of("/assets/crate/crate.normal.png")
            val texture: ReadOnlyTexture = Texture2D.of("/assets/crate/crate.diffuse.png")
            return NormalMappedModel(mesh, texture, normalMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
