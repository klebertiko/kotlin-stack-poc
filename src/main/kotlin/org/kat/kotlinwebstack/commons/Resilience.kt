package org.kat.kotlinwebstack.commons

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry

class Resilience {
    private val circuitBreakerConfig: CircuitBreakerConfig = CircuitBreakerConfig.ofDefaults()

    private val circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig)
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("globalCircuitBreaker", circuitBreakerConfig)
    private var decoratedSupplier = CircuitBreaker.decorateCheckedSupplier(circuitBreaker) { "Hello" }
}