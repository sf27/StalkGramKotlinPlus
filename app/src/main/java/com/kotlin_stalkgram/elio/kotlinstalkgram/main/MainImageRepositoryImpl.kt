package com.kotlin_stalkgram.elio.kotlinstalkgram.main

import android.os.AsyncTask
import com.kotlin_stalkgram.elio.kotlinstalkgram.domain.DownloadFileFromURL
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base.EventBus
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent

/**
 * Created by elio on 8/5/16.
 */
class MainImageRepositoryImpl(private var downloadFileFromURL: DownloadFileFromURL?, private val eventBus: EventBus, private val event: MainEvent) : MainRepository {

    override fun downloadFile(username: String, url: String) {
        if (downloadFileFromURL!!.status == AsyncTask.Status.FINISHED) {
            downloadFileFromURL = DownloadFileFromURL(eventBus, event)
        }
        downloadFileFromURL!!.setEventType(MainEvent.onDownloadImage)
        downloadFileFromURL!!.execute(url)
    }
}
