package org.saar.gui.graphics

import org.joml.Vector2i
import org.saar.core.mesh.Model
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.gui.style.Color
import org.saar.gui.style.Colors
import org.saar.lwjgl.opengl.clear.ClearColor
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.UIntUniform
import org.saar.lwjgl.opengl.shader.uniforms.Vec2iUniform
import org.saar.maths.objects.Polygon

class RenderGraphics : Graphics {

    private val renderList = ArrayList<Model>()

    override var color: Color = Colors.BLACK

    @UniformProperty
    private val windowSizeUniform = object : Vec2iUniform() {
        override val name = "windowSize"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

    @UniformProperty
    private val colorUniform = object : UIntUniform() {
        override val name = "color"

        override val value get() = color.asInt()
    }

    companion object {
        private val vertex = Shader.createVertex(GlslVersion.V400,
            ShaderCode.loadSource("/shaders/gui/graphics/render/graphics.vertex.glsl"))
        private val fragment = Shader.createFragment(GlslVersion.V400,
            ShaderCode.loadSource("/shaders/gui/graphics/render/graphics.fragment.glsl"))
        private val shadersProgram = ShadersProgram.create(vertex, fragment)
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

    override fun clear(clearColor: Color) {
        ClearColor.set(
            clearColor.red / 256f,
            clearColor.green / 256f,
            clearColor.blue / 256f,
            1f)
    }

    override fun process() {

    }

    override fun delete() {
        shadersProgram.delete()
    }
}
