package org.abimon.khroma

import org.abimon.khroma.effects.KhromaCustom
import org.abimon.khroma.keyboards.Key
import org.abimon.khroma.keyboards.KeyboardLayouts
import org.abimon.khroma.keyboards.KeyboardLayouts.enUS
import org.abimon.khroma.responses.RazerChromaResult
import java.awt.Color
import java.awt.event.KeyEvent

typealias KhromaAPIResult=Pair<KhromaAPI?, RazerChromaResult?>
typealias KeyboardLayout=Map<Key, Pair<Int, Int>>
typealias BuilderInit<T> = (T.() -> Unit)

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

/**
 * This gets the positions for these keys; it is *not* (necessarily) the positions of the WASD keys for keyboards other than QWERTY
 */
val KeyboardLayout.WASD: Array<Pair<Int, Int>>
    get() = arrayOf(enUS[Key.W]!!, enUS[Key.A]!!, enUS[Key.S]!!, enUS[Key.D]!!)

/**
 * This gets the positions for these keys; it is *not* (necessarily) the positions of the arrow keys for keyboards other than QWERTY
 */
val KeyboardLayout.ARROWKEYS: Array<Pair<Int, Int>>
    get() = arrayOf(enUS[Key.UP]!!, enUS[Key.LEFT]!!, enUS[Key.DOWN]!!, enUS[Key.RIGHT]!!)

/**
 * This gets the positions for these keys; it is *not* (necessarily) the positions of the numberpad arrow keys for keyboards other than QWERTY
 */
val KeyboardLayout.NUMPAD_ARROWKEYS: Array<Pair<Int, Int>>
    get() = arrayOf(enUS[Key.NUMPAD_UP]!!, enUS[Key.NUMPAD_LEFT]!!, enUS[Key.NUMPAD_DOWN]!!, enUS[Key.NUMPAD_RIGHT]!!)

/**
 * This gets the positions for these keys; it is *not* (necessarily) the positions of the number keys for keyboards other than QWERTY
 */
val KeyboardLayout.NUMBERS: Array<Pair<Int, Int>>
    get() = arrayOf(
        enUS[Key.NUM_0]!!, enUS[Key.NUM_1]!!, enUS[Key.NUM_2]!!,
        enUS[Key.NUM_3]!!, enUS[Key.NUM_4]!!, enUS[Key.NUM_5]!!,
        enUS[Key.NUM_6]!!, enUS[Key.NUM_7]!!, enUS[Key.NUM_8]!!,
        enUS[Key.NUM_9]!!
    )

/**
 * This gets the positions for these keys; it is *not* (necessarily) the positions of the numberpad for keyboards other than QWERTY
 */
val KeyboardLayout.NUMBERPAD: Array<Pair<Int, Int>>
    get() = arrayOf(
        enUS[Key.NUM_LOCK]!!, enUS[Key.NUMPAD_SLASH]!!, enUS[Key.NUMPAD_ASTERISK]!!, enUS[Key.NUMPAD_MINUS]!!,
        enUS[Key.NUMPAD_7]!!, enUS[Key.NUMPAD_8]!!, enUS[Key.NUMPAD_9]!!, enUS[Key.NUMPAD_PLUS]!!,
        enUS[Key.NUMPAD_4]!!, enUS[Key.NUMPAD_5]!!, enUS[Key.NUMPAD_6]!!,
        enUS[Key.NUMPAD_1]!!, enUS[Key.NUMPAD_2]!!, enUS[Key.NUMPAD_3]!!, enUS[Key.NUMPAD_ENTER]!!,
        enUS[Key.NUMPAD_0]!!, enUS[Key.NUMPAD_PERIOD]!!
    )

/**
 * This gets the positions for these keys; it is *not* (necessarily) the positions of the letters for keyboards other than QWERTY
 */
val KeyboardLayout.LETTERS: Array<Pair<Int, Int>>
    get() = arrayOf(
        enUS[Key.Q]!!, enUS[Key.W]!!, enUS[Key.E]!!, enUS[Key.R]!!, enUS[Key.T]!!, enUS[Key.Y]!!, enUS[Key.U]!!, enUS[Key.I]!!, enUS[Key.O]!!, enUS[Key.P]!!,
        enUS[Key.A]!!, enUS[Key.S]!!, enUS[Key.D]!!, enUS[Key.F]!!, enUS[Key.G]!!, enUS[Key.H]!!, enUS[Key.J]!!, enUS[Key.K]!!, enUS[Key.L]!!,
        enUS[Key.Z]!!, enUS[Key.X]!!, enUS[Key.C]!!, enUS[Key.V]!!, enUS[Key.B]!!, enUS[Key.N]!!, enUS[Key.M]!!
    )

/**
 * This gets the positions for these keys; it is *not* (necessarily) the positions of the function keys for keyboards other than QWERTY
 */
val KeyboardLayout.FUNCTIONS: Array<Pair<Int, Int>>
    get() = arrayOf(
        enUS[Key.F1]!!, enUS[Key.F2]!!, enUS[Key.F3]!!, enUS[Key.F4]!!,
        enUS[Key.F5]!!, enUS[Key.F6]!!, enUS[Key.F7]!!, enUS[Key.F8]!!,
        enUS[Key.F9]!!, enUS[Key.F10]!!, enUS[Key.F11]!!, enUS[Key.F12]!!
    )

val KeyboardLayout.ALL: Array<Pair<Int, Int>>
    get() = values.toTypedArray()