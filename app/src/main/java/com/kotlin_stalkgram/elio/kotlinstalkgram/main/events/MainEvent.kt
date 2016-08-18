package com.kotlin_stalkgram.elio.kotlinstalkgram.main.events

/**
 * Created by ykro.
 */
class MainEvent {

    var type: Int = 0
    var progress: Int = 0
    var error: String? = null
    var filePath: String? = null

    init {
        this.type = 0
        this.progress = 0
        this.error = null
        this.filePath = null
    }

    companion object {
        val onDownloadError = 1
        val onDownloadVideoSuccess = 2
        val onDownloadImageSuccess = 3
        val onDownloadImage = 4
        val onDownloadVideo = 5
        val onProgress = 6
    }
}
