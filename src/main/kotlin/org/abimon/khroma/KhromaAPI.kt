package org.abimon.khroma

import awaitObject
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import com.github.kittinunf.fuel.jackson.mapper
import kotlinx.coroutines.*
import org.abimon.khroma.effects.KhromaEffect
import org.abimon.khroma.effects.KhromaEffectBuilder
import org.abimon.khroma.requests.RazerChromaApplication
import org.abimon.khroma.responses.RazerChromaHeartbeat
import org.abimon.khroma.responses.RazerChromaResult
import org.abimon.khroma.responses.RazerChromaSession
import java.util.concurrent.atomic.AtomicBoolean

class KhromaAPI(val uri: String) {
    companion object {
        val BASE_URL = "http://localhost:54235"

        fun initialise(application: RazerChromaApplication, baseURL: String = BASE_URL): KhromaAPIResult = runBlocking { initialiseAsync(application, baseURL) }
        suspend fun initialiseAsync(application: RazerChromaApplication, baseURL: String = BASE_URL): KhromaAPIResult {
            val (session, result) = Fuel.post("$baseURL/razer/chromasdk").jsonBody(application).awaitObjectOrError<RazerChromaSession, RazerChromaResult>()
            if (session != null) {
                return KhromaAPI(session.uri) to null
            } else {
                return null to result
            }
        }

        init {
            mapper
                .registerModules(Jdk8Module(), JavaTimeModule(), ParameterNamesModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        }
    }

    private val _isActive = AtomicBoolean(false)
    public val isActive: Boolean
        get() = _isActive.get()

    private val _isAlive = AtomicBoolean(true)
    public val isAlive: Boolean
        get() = _isAlive.get()

    val keepAliveJob = GlobalScope.launch {
        while (isActive && _isAlive.get()) {
            delay(1000)
            Fuel.put("$uri/heartbeat").awaitObject<RazerChromaHeartbeat>(jacksonDeserializerOf())
            _isActive.set(true)
        }

        uninitialise()
    }

    fun createKeyboardEffectBlocking(effect: KhromaEffect<*>) = runBlocking { createKeyboardEffect(effect) }
    suspend fun createKeyboardEffect(effect: KhromaEffect<*>): RazerChromaResult {
        waitUntilActive()
        return Fuel.put("$uri/keyboard").jsonBody(effect).awaitObject(safeJacksonDeserializerOf())
    }

    fun createKeyboardEffectBlocking(effect: KhromaEffectBuilder) = runBlocking { createKeyboardEffect(effect) }
    suspend fun createKeyboardEffect(effect: KhromaEffectBuilder) {
        waitUntilActive()
        effect.run(this)
    }

    fun createMousepadEffectBlocking(effect: KhromaEffect<*>) = runBlocking { createMousepadEffect(effect) }
    suspend fun createMousepadEffect(effect: KhromaEffect<*>): RazerChromaResult {
        waitUntilActive()
        return Fuel.put("$uri/mousepad").jsonBody(effect).awaitObject(safeJacksonDeserializerOf())
    }

    suspend fun waitUntilActive() {
        while (!isActive) {
            delay(100)
        }
    }

    fun uninitialise() {
        if (_isAlive.get()) {
            Fuel.delete(uri).response()
            _isAlive.set(false)
        }
    }

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            uninitialise()
        })
    }
}