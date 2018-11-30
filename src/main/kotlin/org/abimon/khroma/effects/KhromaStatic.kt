package org.abimon.khroma.effects

import com.fasterxml.jackson.annotation.JsonIgnore
import org.abimon.khroma.bgr
import java.awt.Color

data class KhromaStatic(@JsonIgnore val color: Color): KhromaEffect<Map<String, Int>> {
    override val effect: String = "CHROMA_STATIC"
    override val param: Map<String, Int> = mapOf("color" to color.bgr)
}