package org.abimon.khroma

import awaitByteArray
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import com.github.kittinunf.fuel.jackson.mapper
import com.github.kittinunf.result.Result
import java.io.InputStream
import java.io.Reader
import java.nio.charset.Charset

fun Request.jsonBody(body: Any, charset: Charset = Charsets.UTF_8): Request {
    headers.remove("Content-Type")
    header("Content-Type" to "application/json")
    return body(mapper.writeValueAsString(body), charset)
}

suspend inline fun <reified V: Any, reified E: Any> Request.awaitObjectOrError(objectDeserializer: ResponseDeserializable<V> = safeJacksonDeserializerOf(), errorDeserializer: ResponseDeserializable<E> = safeJacksonDeserializerOf()): Pair<V?, E?> {
    val data = awaitByteArray()
    val objectData = objectDeserializer.deserialize(data)

    if (objectData != null)
        return objectData to null

    val errorData = errorDeserializer.deserialize(data)!!

    return null to errorData
}

inline fun <reified T : Any> safeJacksonDeserializerOf() = object : ResponseDeserializable<T> {
    override fun deserialize(reader: Reader): T? {
        try {
            return mapper.readValue(reader)
        } catch (mapping: JsonMappingException) {
            return null
        }
    }

    override fun deserialize(content: String): T? {
        try {
            return mapper.readValue(content)
        } catch (mapping: JsonMappingException) {
            return null
        }
    }

    override fun deserialize(bytes: ByteArray): T? {
        try {
            return mapper.readValue(bytes)
        } catch (mapping: JsonMappingException) {
            return null
        }
    }

    override fun deserialize(inputStream: InputStream): T? {
        try {
            return mapper.readValue(inputStream)
        } catch (mapping: JsonMappingException) {
            return null
        }
    }
}