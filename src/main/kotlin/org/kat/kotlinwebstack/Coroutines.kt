package org.kat.kotlinwebstack

import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

fun computeWaitTime(multiplier: Double, maxWait: Long, attempt: Long): Long {
    val exp = Math.pow(2.0, attempt.toDouble())
    val result = Math.round(multiplier * exp).coerceAtMost(maxWait)
    return if (result >= 0L) result else 0L
}

data class Attempt(val num: Long, val wait: Long, val e: Exception)

suspend fun <T> infiniteRetry(
    maxWait: Long = 10,
    unit: TimeUnit = TimeUnit.SECONDS,
    factor: Double = 1.0,
    onError: (Attempt) -> Unit = {},
    task: suspend () -> T
): T {
    var attempt = 0L
    var wait = 0L
    while (true) {
        try {
            return task()
        } catch (e: Exception) {
            onError(Attempt(attempt, wait, e))
        }
        delay(wait)
        wait = computeWaitTime(factor, unit.toMillis(maxWait), attempt)
        ++attempt
    }
}

suspend fun <T> retry(
    times: Int = Int.MAX_VALUE,
    initialDelay: Long = 100, // 0.1 second
    maxDelay: Long = 1000,    // 1 second
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            // you can log an error here and/or make a more finer-grained
            // analysis of the cause to see if retry is needed
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}

fun main() {
    GlobalScope.launch {
        //        retry(times = 3) {
//            println("hello")
//        }

        infiniteRetry {
            println("hello")
        }
    }

    GlobalScope.async {
        //        retry(times = 3) {
//            println("hello")
//        }

        infiniteRetry {
            println("hello")
        }
    }

    runBlocking {
        //        retry(times = 3) {
//            println("hello")
//        }

        infiniteRetry {
            println("hello")
        }
    }
}