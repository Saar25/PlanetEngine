package org.saar.core.renderer

import org.saar.core.camera.ICamera
import org.saar.lwjgl.opengl.clipplane.ClipPlane

class RenderContext @JvmOverloads constructor(val camera: ICamera?, val clipPlane: ClipPlane? = null)
