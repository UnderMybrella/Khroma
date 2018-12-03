package org.abimon.khroma.effects

import kotlinx.coroutines.delay
import org.abimon.khroma.BuilderInit
import org.abimon.khroma.KhromaAPI
import org.abimon.khroma.bgr
import org.abimon.khroma.keyboards.KhromaFrame
import java.awt.Color

abstract class KhromaEffectBuilder {
    var startingFrame: KhromaFrame? = null
    val frame: KhromaFrame by lazy { startingFrame ?: KhromaFrame() }
    var startingDelay: Long = 0L
    var followedBy: KhromaEffectBuilder? = null

    suspend fun run(api: KhromaAPI) {
        delay(startingDelay)
        runEffect(api)
        followedBy?.startingFrame = frame
        followedBy?.run(api)
    }

    abstract suspend fun runEffect(api: KhromaAPI)
}

open class StaticEffectBuilder : KhromaEffectBuilder() {
    var colour: Color = Color.BLUE

    override suspend fun runEffect(api: KhromaAPI) {
        KhromaFrame.INDICES.forEach { (x, y) -> frame[x, y] = colour }
        api.createKeyboardEffect(KhromaStatic(colour))
    }
}

open class RippleEffectBuilder : KhromaEffectBuilder() {
    var width: Int = 1
    val colours: MutableList<Color> = ArrayList()
    var colour: Color
        get() = colours.firstOrNull() ?: Color.BLACK
        set(value) {
            colours.clear()
            colours.add(value)
        }

    var startingKey: Pair<Int, Int> = 8 to 3
    var delayBetweenRipples: Long = 50L
    var persistRipples: Boolean = false
    val affectedKeys: MutableList<Pair<Int, Int>> = ArrayList()

    fun withKeys(keyInit: MutableList<Pair<Int, Int>>.() -> Unit) {
        affectedKeys.keyInit()
    }

    fun withKeys(vararg newKeys: Pair<Int, Int>) {
        affectedKeys.addAll(newKeys)
    }

    fun withColours(colourInit: MutableList<Color>.() -> Unit) {
        colours.colourInit()
    }

    fun withColours(vararg newColours: Color) {
        colours.addAll(newColours)
    }

    fun nextColour(colourIndex: Int): Int {
        return if (colourIndex < colours.size) colours[colourIndex].bgr else colours.lastOrNull()?.bgr
            ?: Color.GREEN.bgr
    }

    override suspend fun runEffect(api: KhromaAPI) {
        var colourIndex: Int = 0
        val positions = HashSet<Pair<Int, Int>>()
        val oldPositions = HashSet<Pair<Int, Int>>()
        val oldRings = ArrayList<List<Pair<Int, Int>>>()
        positions.add(startingKey)

        val ourFrame = Array(6) { IntArray(22) }
        KhromaFrame.INDICES.forEach { (x, y) -> ourFrame[y][x] = frame[x, y] }

        while (oldPositions.size < 132 || oldRings.any(List<*>::isNotEmpty)) {
            val colour = nextColour(colourIndex++)
            positions.filter(affectedKeys::contains).forEach { (x, y) -> ourFrame[y][x] = colour }
            api.createKeyboardEffect(KhromaCustom(ourFrame))

            oldPositions.addAll(positions)

            val thisRing = positions.toTypedArray().flatMap { (x, y) ->
                arrayOf((x - 1) to y, (x + 1) to y, x to (y - 1), x to (y + 1)).filterNot(oldPositions::contains)
            }
            positions.addAll(thisRing)

            if (!persistRipples) {
                KhromaFrame.INDICES.forEach { (x, y) -> ourFrame[y][x] = frame[x, y] }
                positions.removeAll(oldPositions)
                (0 until (oldRings.size - (width - 1))).forEach { oldRings.removeAt(0) }
                oldRings.forEach { ring -> positions.addAll(ring) }
                oldRings.add(thisRing)
            }

            positions.removeAll { (x, y) -> x !in 0 until 22 || y !in 0 until 6 }

            delay(delayBetweenRipples)
        }

        frame overwriteWith ourFrame
    }
}

open class WaveEffectBuilder : KhromaEffectBuilder() {
    var width: Int = 1
    val colours: MutableList<Color> = ArrayList()
    var colour: Color
        get() = colours.firstOrNull() ?: Color.BLACK
        set(value) {
            colours.clear()
            colours.add(value)
        }

    var horizontal: Boolean = true

    var startingIndex: Int = 0
    var delayBetweenWaves: Long = 50L
    var persistWaves: Boolean = false
    val affectedKeys: MutableList<Pair<Int, Int>> = ArrayList()

    fun withKeys(keyInit: MutableList<Pair<Int, Int>>.() -> Unit) {
        affectedKeys.keyInit()
    }

    fun withKeys(vararg newKeys: Pair<Int, Int>) {
        affectedKeys.addAll(newKeys)
    }

    fun withColours(colourInit: MutableList<Color>.() -> Unit) {
        colours.colourInit()
    }

    fun withColours(vararg newColours: Color) {
        colours.addAll(newColours)
    }

    fun nextColour(colourIndex: Int): Int {
        return if (colourIndex < colours.size) colours[colourIndex].bgr else colours.lastOrNull()?.bgr
            ?: Color.GREEN.bgr
    }

    override suspend fun runEffect(api: KhromaAPI) {
        var colourIndex: Int = 0

        val ourFrame = Array(6) { IntArray(22) }
        KhromaFrame.INDICES.forEach { (x, y) -> ourFrame[y][x] = frame[x, y] }

        if (horizontal) {
            for (sx in 0 until 22) {
                val colour = nextColour(colourIndex++)

                (0 until 6).map(sx::to).filter(affectedKeys::contains).forEach { (x, y) -> ourFrame[y][x] = colour }
                api.createKeyboardEffect(KhromaCustom(ourFrame))

                delay(delayBetweenWaves)
            }
        } else {
            for (sy in 0 until 6) {
                val colour = nextColour(colourIndex++)

                (0 until 22).map { x -> x to sy }.filter(affectedKeys::contains).forEach { (x, y) -> ourFrame[y][x] = colour }
                api.createKeyboardEffect(KhromaCustom(ourFrame))

                delay(delayBetweenWaves)
            }
        }

        frame overwriteWith ourFrame
    }
}

fun buildStaticEffect(init: BuilderInit<StaticEffectBuilder>): StaticEffectBuilder {
    val builder = StaticEffectBuilder()
    builder.init()
    return builder
}

fun buildRippleEffect(init: BuilderInit<RippleEffectBuilder>): RippleEffectBuilder {
    val builder = RippleEffectBuilder()
    builder.init()
    return builder
}

fun buildWaveEffect(init: BuilderInit<WaveEffectBuilder>): WaveEffectBuilder {
    val builder = WaveEffectBuilder()
    builder.init()
    return builder
}