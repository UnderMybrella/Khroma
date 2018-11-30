package org.abimon.khroma.effects

import com.fasterxml.jackson.annotation.JsonIgnore

data class KhromaCustomKey(@JsonIgnore val colours: Array<IntArray>, @JsonIgnore val keys: Array<IntArray>): KhromaEffect<Map<String, Array<IntArray>>> {
    override val effect: String = "CHROMA_CUSTOM"
    override val param: Map<String, Array<IntArray>> = mapOf("color" to colours, "key" to keys)

    init {
        check(colours.size == 6)
        check(colours.all { array -> array.size == 22 })

        check(keys.size == 6)
        check(keys.all { array -> array.size == 22 })
    }
}