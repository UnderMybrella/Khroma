package org.abimon.khroma.keyboards

import org.abimon.khroma.bgr
import java.awt.Color

data class KhromaFrame(val rows: Array<IntArray> = Array(ROW_LIMIT) { IntArray(
    COLUMN_LIMIT
) }, val layout: Map<Key, Pair<Int, Int>> = KeyboardLayouts.enUS) {
    companion object {
        val ROW_LIMIT = 6
        val COLUMN_LIMIT = 22

        val INDICES = (0 until ROW_LIMIT).flatMap { y -> (0 until COLUMN_LIMIT).map { x -> x to y } }

        operator fun MutableMap<Key, Pair<Int, Int>>.set(vararg keyCodes: Key, coords: Pair<Int, Int>) = keyCodes.forEach { code -> set(code, coords) }
    }

    val columns: Array<IntArray>
        get() = (0 until COLUMN_LIMIT).map { i -> rows.map { column -> column[i] }.toIntArray() }.toTypedArray()

    operator fun get(index: Int): IntArray = rows[index]
    operator fun set(index: Int, array: IntArray) = rows.set(index, array)

    operator fun get(code: Key): Int? {
        val coords: Pair<Int, Int>

        when (code) {
            in layout -> coords = layout[code]!!
            else -> return null
        }

        return get(coords.first, coords.second)
    }

    operator fun set(code: Key, value: Color) = set(code, value.bgr)
    operator fun set(code: Key, value: Int) {
        val coords: Pair<Int, Int>

        when (code) {
            in layout -> coords = layout[code]!!
            else -> return
        }

        set(coords.first, coords.second, value)
    }
    operator fun set(x: Int, y: Int, value: Color) = this[y].set(x, value.bgr)
    operator fun set(x: Int, y: Int, value: Int) = this[y].set(x, value)

    operator fun get(x: Int, y: Int): Int = this[y][x]

    infix fun overwriteWith(frame: KhromaFrame) {
        INDICES.forEach { (x, y) -> this[x, y] = frame[x, y] }
    }

    infix fun overwriteWith(rows: Array<IntArray>) {
        INDICES.forEach { (x, y) -> this[x, y] = rows[y][x] }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KhromaFrame

        if (!rows.contentDeepEquals(other.rows)) return false

        return true
    }

    override fun hashCode(): Int {
        return rows.contentDeepHashCode()
    }
}