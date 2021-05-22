package org.saar.gui.graphics

import org.joml.Vector2i
import org.joml.Vector2ic
import org.saar.core.mesh.Model
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.gui.style.Colour
import org.saar.gui.style.Colours
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.UIntUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.objects.Polygon
import java.util.*

class RenderGraphics : Graphics {

    private val windowSize: Vector2i = Vector2i()

    private val renderList = ArrayList<Model>()

    private var colour: Colour = Colours.BLACK

    @UniformProperty
    private val windowSizeUniform = object : Vec2iUniform() {
        override fun getName(): String = "windowSize"

        override fun getUniformValue(): Vector2ic {
            val width = Window.current().width
            val height = Window.current().height
            return windowSize.set(width, height)
        }
    }

    @UniformProperty
    private val colourUniform = object : UIntUniform() {
        override fun getName(): String = "colour"

        override fun getUniformValue(): Int = colour.asInt()
    }

    companion object {
        private val vertex = Shader.createVertex(GlslVersion.V400,
            ShaderCode.loadSource("/shaders/gui/graphics/render/graphics.vertex.glsl"))
        private val fragment = Shader.createFragment(GlslVersion.V400,
            ShaderCode.loadSource("/shaders/gui/graphics/render/graphics.fragment.glsl"))
        private val shadersProgram = ShadersProgram.create(vertex, fragment)
    }

    override fun setColour(colour: Colour) {
        this.colour = colour
    }

    override fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        /*val line = Line(x1.toFloat() / windowSize.x, y1.toFloat() / windowSize.y,
                x2.toFloat() / windowSize.x, y2.toFloat() / windowSize.y)
        renderList.add(line)*/
    }

    override fun drawRectangle(x: Int, y: Int, w: Int, h: Int) {

    }

    override fun fillRectangle(x: Int, y: Int, w: Int, h: Int) {

    }

    override fun drawOval(cx: Int, cy: Int, a: Int, b: Int) {

    }

    override fun fillOval(cx: Int, cy: Int, a: Int, b: Int) {

    }

    override fun fillPolygon(polygon: Polygon) {

    }

    override fun clear(clearColour: Colour) {
        GlUtils.setClearColour(
            clearColour.red / 256f,
            clearColour.green / 256f,
            clearColour.blue / 256f,
            1f)
    }

    override fun process() {

    }

    override fun delete() {
        shadersProgram.delete()
    }
}
