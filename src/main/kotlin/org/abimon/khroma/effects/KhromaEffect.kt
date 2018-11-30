package org.abimon.khroma.effects


interface KhromaEffect<T: Any?> {
    val effect: String

    val param: T
}