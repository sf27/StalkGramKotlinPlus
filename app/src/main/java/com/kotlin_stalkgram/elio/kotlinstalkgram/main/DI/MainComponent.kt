package com.kotlin_stalkgram.elio.kotlinstalkgram.main.DI

import com.kotlin_stalkgram.elio.kotlinstalkgram.main.DI.MainModule
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.MainPresenter
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.DI.LibsModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by elio on 8/5/16.
 */
@Singleton
@Component(modules = arrayOf(MainModule::class, LibsModule::class))
interface MainComponent {

    //    void inject(MainActivity activity);
    val presenter: MainPresenter
}