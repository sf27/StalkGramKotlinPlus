package com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base

/**
 * Created by ykro.
 */
interface EventBus {
    fun register(subscriber: Any)
    fun unregister(subscriber: Any)
    fun post(event: Any)
}
