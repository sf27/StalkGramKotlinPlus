package com.kotlin_stalkgram.elio.kotlinstalkgram.lib.DI

import dagger.Component
import javax.inject.Singleton

/**
 * Created by ykro.
 */

@Singleton
@Component(modules = arrayOf(LibsModule::class))
interface LibsComponent

