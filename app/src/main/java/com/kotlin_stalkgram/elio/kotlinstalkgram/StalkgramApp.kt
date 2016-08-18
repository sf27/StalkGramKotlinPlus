package com.kotlin_stalkgram.elio.kotlinstalkgram

import android.app.Application
import android.content.Context
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.DI.LibsModule
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.DI.DaggerMainComponent
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.DI.MainComponent
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.DI.MainModule
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.UI.MainView

class StalkgramApp : Application() {

    fun getMainComponent(view: MainView, context: Context): MainComponent {
        return DaggerMainComponent.builder().
                libsModule(LibsModule()).
                mainModule(MainModule(view, context))
                .build()
    }
}
