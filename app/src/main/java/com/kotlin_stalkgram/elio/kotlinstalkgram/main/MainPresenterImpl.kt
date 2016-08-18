package com.kotlin_stalkgram.elio.kotlinstalkgram.main

import android.content.Context
import android.os.AsyncTask
import com.kotlin_stalkgram.elio.kotlinstalkgram.domain.DownloadPageAndParseHtml
import com.kotlin_stalkgram.elio.kotlinstalkgram.domain.FileUtils
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base.EventBus
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.UI.MainView
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent
import org.greenrobot.eventbus.Subscribe

/**
 * Created by elio on 8/5/16.
 */
class MainPresenterImpl(private val context: Context, private val eventBus: EventBus, mainView: MainView, private val mainInteractor: MainInteractor, private var parseHtml: DownloadPageAndParseHtml?) : MainPresenter, DownloadPageAndParseHtml.onCompletedCallback {
    override var view: MainView? = null
        private set
    var filePath: String? = null

    init {
        this.view = mainView
    }

    override fun onCreate() {
        filePath = null
        eventBus.register(this)
    }

    override fun onDestroy() {
        filePath = null
        view = null
        eventBus.unregister(this)
    }

    override fun downloadFile(url: String) {
        if (view != null) {
            view!!.disableInputs()
            view!!.showProgress()
        }
        if (parseHtml!!.status == AsyncTask.Status.FINISHED) {
            parseHtml = DownloadPageAndParseHtml(context)
        }
        parseHtml!!.setOnCompleteCallback(this)
        parseHtml!!.execute(url)
    }

    override fun shareFile() {
        val path = filePath
        if (path != null) {
            FileUtils.shareFile(path, context)
        }
    }

    override fun setAsFile() {
        val path = filePath
        if (path != null) {
            FileUtils.setImageAs(path, context)
        }
    }

    @Subscribe
    override fun onEventMainThread(event: MainEvent) {
        when (event.type) {
            MainEvent.onProgress -> onProgress(event.progress)
            MainEvent.onDownloadError -> onDownloadError(event.error.toString())
            MainEvent.onDownloadImageSuccess -> onDownloadImageSuccess(event.filePath.toString())
            MainEvent.onDownloadVideoSuccess -> onDownloadVideoSuccess(event.filePath.toString())
        }
    }

    override fun checkIfStorageIsAvailable() {
    }

    private fun onDownloadError(error: String) {
        if (view != null) {
            view!!.hideProgress()
            view!!.enableInputs()
            view!!.downloadError(error)
        }
    }

    private fun onDownloadImageSuccess(imagePath: String) {
        if (view != null) {
            view!!.hideProgress()
            view!!.enableInputs()
            view!!.downloadImageSuccess(imagePath)
            filePath = imagePath
        }
    }

    private fun onDownloadVideoSuccess(videoPath: String) {
        if (view != null) {
            view!!.hideProgress()
            view!!.enableInputs()
            view!!.downloadVideoSuccess(videoPath)
            filePath = videoPath
        }
    }

    private fun onProgress(progress: Int) {
        if (view != null) {
            view!!.onProgress(progress)
        }
    }

    override fun onComplete(data: Map<String, String>) {
        mainInteractor.downloadFile(data)
    }
}
