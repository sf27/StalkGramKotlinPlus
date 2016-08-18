package com.kotlin_stalkgram.elio.kotlinstalkgram.main.UI

interface MainView {
    fun enableInputs()
    fun disableInputs()

    fun showProgress()
    fun onProgress(progress: Int)
    fun hideProgress()

    fun onDownload()

    fun downloadImageSuccess(imagePath: String)
    fun downloadVideoSuccess(videoPath: String)
    fun downloadError(error: String)
}