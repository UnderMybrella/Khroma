package org.abimon.khroma.effects

import org.abimon.khroma.keyboards.KhromaFrame

data class KhromaCustom(override val param: Array<IntArray>): KhromaEffect<Array<IntArray>> {
    constructor(frame: KhromaFrame): this(frame.rows)
    override val effect: String = "CHROMA_CUSTOM"

    init {
        check(param.size == 6)
        check(param.all { array -> array.size == 22 })
    }
}