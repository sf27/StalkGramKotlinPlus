package com.kotlin_stalkgram.elio.kotlinstalkgram.lib.DI

import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.GreenRobotEventBus
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base.EventBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ykro.
 */
@Module
class LibsModule {
    @Provides
    @Singleton
    fun provideEventBus(): EventBus {
        return GreenRobotEventBus()
    }
}
