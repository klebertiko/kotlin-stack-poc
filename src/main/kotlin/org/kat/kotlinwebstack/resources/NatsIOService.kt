package org.kat.kotlinwebstack.resources

import io.nats.client.Connection
import io.nats.client.Message
import io.nats.client.Nats
import io.nats.client.Subscription
import java.io.IOException
import java.time.Duration
import java.util.*

class NatsIOService {

    private val serverURI: String = "jnats://localhost:4222"
    private val natsConnection: Connection = Nats.connect(serverURI)
    private val subscriptions = HashMap<String, Subscription>()

    fun publishMessage(topic: String, replyTo: String, message: String) {
        try {
            natsConnection.publish(topic, replyTo, message.toByteArray())
        } catch (ioe: IOException) {
            error("Error publishing message: {} to {} ")
        }
    }

    fun makeRequest(topic: String, request: String): Message {
        return natsConnection.request(topic, request.toByteArray(), Duration.ofSeconds(100))
    }

    fun subscribeSync(topic: String): Subscription {
        return natsConnection.subscribe(topic)
    }

    fun subscribeAsync(topic: String) {
        val subscription = natsConnection.subscribe(topic)

        if (subscription == null) {
            error("Error subscribing to {}")
        } else {
            this.subscriptions[topic] = subscription
        }
    }

    fun joinQueueGroup(topic: String, queue: String): Subscription {
        return natsConnection.subscribe(topic, queue)
    }

    fun unsubscribe(topic: String) {
        try {
            val subscription = subscriptions[topic]

            if (subscription != null) {
                subscription.unsubscribe()
            } else {
                error("{} not found. Unable to unsubscribe.")
            }
        } catch (ioe: IOException) {
            error("Error unsubscribing from {} ")
        }
    }
}