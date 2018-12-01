package org.abimon.khroma

import org.abimon.khroma.effects.KhromaCustom
import org.abimon.khroma.responses.RazerChromaResult
import java.awt.Color
import java.awt.event.KeyEvent

typealias KhromaAPIResult=Pair<KhromaAPI?, RazerChromaResult?>

val KhromaAPIResult.api: KhromaAPI
    get() = first!!

val KhromaAPIResult.result: KhromaAPI
    get() = first!!

val Color.bgr: Int
    get() = (blue and 0xFF shl 16) or
            (green and 0xFF shl 8) or
            (red and 0xFF shl 0)

suspend fun KhromaAPI.rippleOut(colour: Color, delay: Long = 50) {
    val positions = HashSet<Pair<Int, Int>>()
    positions.add(8 to 3)

    val frame = Array(6) { IntArray(22) }
    while (frame.any { array -> array.any(0::equals) }) {
        positions.forEach { (x, y) -> frame[y][x] = colour.bgr }
        createKeyboardEffect(KhromaCustom(frame))

        positions.toTypedArray().forEach { (x, y) ->
            positions.addAll(arrayOf((x - 1) to y, (x + 1) to y, x to (y - 1), x to (y + 1)))
        }

        positions.removeAll { (x, y) -> x !in 0 until 22 || y !in 0 until 6 }

        kotlinx.coroutines.delay(delay)
    }
}
//operator fun KhromaFrame.set(char: Char, value: Color) = set(char, value.bgr)
//operator fun KhromaFrame.set(char: Char, value: Int) {
//
//}