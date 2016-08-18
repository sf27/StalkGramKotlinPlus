package com.kotlin_stalkgram.elio.kotlinstalkgram.domain

import android.os.AsyncTask
import android.util.Log
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base.EventBus
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent.Companion.onDownloadImage
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent.Companion.onDownloadVideo
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadFileFromURL(private val eventBus: EventBus, private val event: MainEvent) : AsyncTask<String, Int, String>() {

    private var eventType: Int = 0

    fun setEventType(eventType: Int) {
        this.eventType = eventType
    }

    /**
     * Downloading file in background thread
     */
    override fun doInBackground(vararg f_url: String): String? {
        var count: Int = 0
        var absPath: String? = null
        val name: String
        var path: String? = null
        try {
            val url_str = f_url[0]
            val url = URL(url_str)
            val connection = url.openConnection()
            connection.connect()
            // getting file length
            val lengthOfFile = connection.contentLength

            // input stream to read file - with 8k buffer
            val input = BufferedInputStream(url.openStream(), 8192)

            when (eventType) {
                onDownloadImage -> {
                    name = "image_" + System.currentTimeMillis() + ".jpg"
                    path = AppConstant.DOWNLOAD_DIRECTORY_IMAGES + "/"
                    absPath = path + "/" + name
                }
                onDownloadVideo -> {
                    name = "video_" + System.currentTimeMillis() + ".mp4"
                    path = AppConstant.DOWNLOAD_DIRECTORY_VIDEOS + "/"
                    absPath = path + "/" + name
                }
            }

            val f = File(path!!)
            if (!f.exists()) {
                f.mkdirs()
            }

            val output = FileOutputStream(absPath!!)
            val data = ByteArray(1024)
            var total: Long = 0

            do {
                count = input.read(data)
                total += count.toLong()
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress((total * 100 / lengthOfFile).toInt())

                // writing data to file
                if (count != -1){
                    output.write(data, 0, count)
                }
            } while ((count) != -1)

//            while ((count) != -1) {
//                count = input.read(data)
//                total += count.toLong()
//                // publishing the progress....
//                // After this onProgressUpdate will be called
//                publishProgress((total * 100 / lengthOfFile).toInt())
//
//                // writing data to file
//                output.write(data, 0, count)
//            }

            // flushing output
            output.flush()
            // closing streams
            output.close()
            input.close()
        } catch (e: Exception) {
            Log.e("Error: ", e.message)
            postError(e.message.toString())
            return null
        }

        return absPath
    }

    /**
     * Updating progress bar
     */
    override fun onProgressUpdate(vararg values: Int?) {
        postProgress(values[0] as Int)
    }

    /**
     * After completing background task Dismiss the progress dialog
     */
    override fun onPostExecute(filePath: String?) {
        if (filePath != null) {
            when (eventType) {
                onDownloadImage -> postImageSuccess(filePath)

                onDownloadVideo -> postVideoSuccess(filePath)
            }
        }
    }

    fun postImageSuccess(filePath: String) {
        post(filePath, MainEvent.onDownloadImageSuccess, null, 0)
    }

    fun postVideoSuccess(filePath: String) {
        post(filePath, MainEvent.onDownloadVideoSuccess, null, 0)
    }

    fun postError(error: String) {
        post(null, MainEvent.onDownloadError, error, 0)
    }

    fun postProgress(progress: Int) {
        post(null, MainEvent.onProgress, null, progress)
    }

    private fun post(filePath: String?, type: Int, error: String?, progress: Int) {
        event.type = type
        event.error = error
        event.filePath = filePath
        event.progress = progress
        eventBus.post(event)
    }
}

