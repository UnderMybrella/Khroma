package org.abimon.khroma.effects

data class KhromaCustom(override val param: Array<IntArray>): KhromaEffect<Array<IntArray>> {
    override val effect: String = "CHROMA_CUSTOM"

    init {
        check(param.size == 6)
        check(param.all { array -> array.size == 22 })
    }
}