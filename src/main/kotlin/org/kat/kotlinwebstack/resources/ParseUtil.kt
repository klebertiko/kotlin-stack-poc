@file:JvmName("ParserUtil")

package org.kat.kotlinwebstack.resources

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.nio.file.Paths

inline fun <reified T : Any> String.deserialize(): T = jacksonObjectMapper().registerModule(KotlinModule()).readValue(this)

inline fun <reified T: Any> T.toJsonString(): String = jacksonObjectMapper().registerModule(KotlinModule()).writeValueAsString(this)

fun <T> String.toJsonObject(valueType: Class<T>): T = jacksonObjectMapper().registerModule(KotlinModule()).readValue(this, valueType)

fun <T> fromJsonFile(jsonFilePath: String, valueType: Class<T>): T = jacksonObjectMapper().registerModule(KotlinModule()).readValue(Paths.get(jsonFilePath).toFile(), valueType)