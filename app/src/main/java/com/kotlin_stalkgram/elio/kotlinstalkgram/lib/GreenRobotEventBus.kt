package com.kotlin_stalkgram.elio.kotlinstalkgram.lib

import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base.EventBus

/**
 * Created by ykro.
 */
class GreenRobotEventBus : EventBus {
    var eventBus: org.greenrobot.eventbus.EventBus

    init {
        eventBus = org.greenrobot.eventbus.EventBus.getDefault()
    }

    override fun register(subscriber: Any) {
        eventBus.register(subscriber)
    }

    override fun unregister(subscriber: Any) {
        eventBus.unregister(subscriber)
    }

    override fun post(event: Any) {
        eventBus.post(event)
    }
}
