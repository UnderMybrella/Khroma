package org.abimon.khroma

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.github.kittinunf.fuel.jackson.mapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import org.abimon.khroma.effects.KhromaCustom
import org.abimon.khroma.effects.KhromaNone
import org.abimon.khroma.effects.KhromaStatic
import org.abimon.khroma.requests.RazerChromaDevice
import org.abimon.khroma.requests.buildChromaApplication
import java.awt.Color
import java.awt.Point
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

object Khroma {
    @JvmStatic
    fun main(args: Array<String>) {
        mapper
            .registerModules(Jdk8Module(), JavaTimeModule(), ParameterNamesModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)

        val apiResponse = KhromaAPI.initialise(buildChromaApplication {
            title = "Khroma Test"
            description = "This is a test for using Khroma"
            author = buildAuthor {
                name = "UnderMybrella"
                contact = "https://abimon.org"
            }
            device_supported.add(RazerChromaDevice.KEYBOARD)
            device_supported.add(RazerChromaDevice.MOUSEPAD)
        })

        println(apiResponse.first?.uri)

        val khroma = apiResponse.api

        runBlocking {
            delay(2000)
            khroma.createKeyboardEffect(KhromaNone)

//            val originalColours = arrayOf(
//                Color.RED,
//                Color.GREEN,
//                Color.BLUE
//            )
//
//            val colours = Array<Color>(22) { index ->
//                return@Array originalColours[floor(index / ceil(22.0 / originalColours.size)).roundToInt()]
//            }
//
//            while (isActive) {
//                for (i in 0 until 22) {
//                    khroma.createKeyboardEffect(KhromaCustom(Array(6) { IntArray(22) { index -> colours[(index + i) % 22].bgr } }))
//                    delay(16)
//                }
//            }

            khroma.rippleOut(Color.RED)

            delay(1000 * 5)

            khroma.uninitialise()

            delay(1000 * 2)
        }
    }
}