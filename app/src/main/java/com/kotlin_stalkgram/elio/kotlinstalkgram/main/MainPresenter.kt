package com.kotlin_stalkgram.elio.kotlinstalkgram.main

import com.kotlin_stalkgram.elio.kotlinstalkgram.main.UI.MainView
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent

/**
 * Created by elio on 8/5/16.
 */
interface MainPresenter {
    fun onCreate()
    fun onDestroy()

    fun downloadFile(url: String)
    fun shareFile()
    fun setAsFile()
    fun onEventMainThread(event: MainEvent)

    fun checkIfStorageIsAvailable()

    val view: MainView?
}
