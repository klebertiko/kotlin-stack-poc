@file:JvmName("ParserUtil")

package util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Paths


inline fun <reified T : Any> String.deserialize(): T = jacksonObjectMapper().readValue(this)

fun <T> T.toJsonString(): String {
    return jacksonObjectMapper().writeValueAsString(this)
}

fun <T> String.toJsonObject(valueType: Class<T>): T {
    return jacksonObjectMapper().readValue(this, valueType)
}

fun <T> fromJsonFile(jsonFilePath: String, valueType: Class<T>): T {
    return jacksonObjectMapper().readValue(Paths.get(jsonFilePath).toFile(), valueType)
}