package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIContainer
import org.saar.gui.UIDisplay
import org.saar.gui.UITextElement
import org.saar.gui.style.Colours
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.LengthValues.percent
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object TextExample {

    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window)

        val container = UIContainer().apply {
            style.x.value = center()
            style.y.value = center()
            style.width.value = percent(90f)
            style.height.value = percent(90f)
        }

        display.add(container)

        val text = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer iaculis ultricies pellentesque. Vestibulum
            vel neque porta, convallis lacus non, consequat libero. Nullam mollis augue nibh, viverra volutpat purus
            vulputate vel. In hac habitasse platea dictumst. Orci varius natoque penatibus et magnis dis parturient
            montes, nascetur ridiculus mus. Donec eu blandit mauris. Sed fringilla mauris ut gravida cursus. Aliquam
            leo ex, viverra sed laoreet id, dignissim nec metus. Maecenas ut nulla in nibh sagittis tempor sit amet vel
            turpis. Praesent iaculis felis vitae sapien aliquam scelerisque. Nunc a dictum risus. Donec rhoncus nulla
            eu metus lobortis sollicitudin. In id elit consequat tortor iaculis rhoncus. Fusce commodo mauris ac nibh
            pulvinar accumsan.
            Donec sit amet tellus ac urna cursus suscipit. Sed lorem dolor, ullamcorper cursus faucibus in, pulvinar
            eget tellus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae;
            Praesent mauris ligula, blandit a est vel, consectetur mattis est. Aenean eu mi varius, consectetur dui
            sed, placerat nibh. Aenean ultricies leo vel tincidunt pretium. Praesent et consectetur elit, quis
            fermentum nisi. Donec congue euismod sollicitudin. Donec at nulla at lacus porttitor facilisis a sed dui.
            Donec facilisis nulla id magna aliquam, sed consectetur metus pellentesque. Lorem ipsum dolor sit amet,
            consectetur adipiscing elit.
            Suspendisse lacinia faucibus velit, eu viverra magna tincidunt vitae. Nulla cursus, ipsum eget iaculis
            tristique, sem erat tempor lacus, eget viverra est lectus ac urna. Praesent cursus dictum tincidunt. Nullam
            aliquam dignissim mauris at luctus. Phasellus ornare ullamcorper ipsum id volutpat. Pellentesque eget felis
            fringilla, consequat nisl at, mollis eros. Nullam auctor congue sem in consequat. Nulla hendrerit aliquam
            purus. Vivamus ultricies felis quis sagittis tincidunt. Praesent et ultricies erat. Morbi in libero non
            nisi pretium rhoncus. Suspendisse id sagittis felis. Maecenas mauris elit, fringilla id dolor in, vehicula
            dignissim tortor.
            Vivamus varius lacinia mattis. Donec eu libero eget nunc accumsan convallis quis eu mauris. Donec
            consectetur dignissim augue et pharetra. Quisque placerat diam ut pellentesque commodo. Cras quam tellus,
            mattis vel massa eget, semper vulputate eros. Aliquam id lectus sit amet ligula laoreet dictum. Aenean vel
            est tristique lacus accumsan convallis. Proin fringilla massa blandit ultricies tristique. Phasellus
            fermentum neque eget metus porta, eget dictum quam aliquam. Vivamus ornare laoreet elit, nec pharetra
            nulla. Mauris magna sapien, pellentesque ac mattis in, vulputate sed elit.
            Quisque blandit ut libero quis imperdiet. Suspendisse potenti. Duis sodales dui lorem, at malesuada justo
            commodo a. Donec quis ligula finibus, porttitor nulla sit amet, feugiat turpis. Nunc et risus hendrerit,
            mattis augue vulputate, dapibus velit. Quisque id risus eget turpis laoreet eleifend. Nullam justo eros,
            commodo pharetra egestas in, mattis ac nisl. Cras vulputate, leo pellentesque porta varius, eros nibh
            mattis sapien, nec ultricies mi nibh at ante. Quisque maximus porttitor dolor, non sagittis metus porttitor a.
        """.trimIndent().replace('\n', ' ')

        val textElement = UITextElement(text).apply {
            style.x.value = center()
            style.y.value = center()
            style.fontColour.set(Colours.WHITE)
            style.fontSize.set(24)
        }
        container.add(textElement)

        val keyboard = window.keyboard

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clear(GlBuffer.COLOUR)
            display.render(RenderContext(null))

            window.swapBuffers()
            window.pollEvents()
        }

        display.delete()
        window.destroy()
    }
}