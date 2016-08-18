package com.kotlin_stalkgram.elio.kotlinstalkgram.main

import android.content.Context
import com.kotlin_stalkgram.elio.kotlinstalkgram.R
import com.kotlin_stalkgram.elio.kotlinstalkgram.domain.ScrappingInstagram
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base.EventBus
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent

/**
 * Created by elio on 8/5/16.
 */
class MainInteractorImpl(private val context: Context, private val mainImageRepository: MainRepository, private val mainVideoRepository: MainRepository, private val eventBus: EventBus, private val event: MainEvent) : MainInteractor {
    override fun downloadFile(data: Map<String, String>?) {
        if (data != null) {
            val username = data[ScrappingInstagram.USERNAME_KEY]
            val videoUrl = data[ScrappingInstagram.VIDEO_KEY]
            val imageUrl = data[ScrappingInstagram.IMAGE_KEY]

            if (!videoUrl.equals("null")) {
                mainVideoRepository.downloadFile(username.toString(), videoUrl.toString())
            } else {
                mainImageRepository.downloadFile(username.toString(), imageUrl.toString())
            }
        } else {
            event.type = MainEvent.onDownloadError
            event.error = context.getString(R.string.event_error_not_return_data)
            eventBus.post(event)
        }
    }
}
