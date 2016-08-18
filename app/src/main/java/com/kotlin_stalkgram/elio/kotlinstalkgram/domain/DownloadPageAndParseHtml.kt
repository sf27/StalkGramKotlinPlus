package com.kotlin_stalkgram.elio.kotlinstalkgram.domain

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import com.kotlin_stalkgram.elio.kotlinstalkgram.R

class DownloadPageAndParseHtml(private val context: Context) : AsyncTask<String, Int, Map<String, String>>() {
    private var pDialog: ProgressDialog? = null
    private var onCompleteCallback: onCompletedCallback? = null

    fun setOnCompleteCallback(onCompleteCallback: onCompletedCallback) {
        this.onCompleteCallback = onCompleteCallback
    }

    /**
     * Before starting background thread Show Progress Bar Dialog
     */
    override fun onPreExecute() {
        super.onPreExecute()
        val msn = this.context.resources.getString(R.string.download_and_parse_progress)
        pDialog = ProgressDialog(this.context)
        pDialog!!.setMessage(msn)
        pDialog!!.isIndeterminate = false
        pDialog!!.max = 100
        pDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pDialog!!.setCancelable(true)
        pDialog!!.show()
    }

    /**
     * Downloading file in background thread
     */
    override fun doInBackground(vararg urls: String): Map<String, String> {
        val url = urls[0]
        val scrappingInstagram = ScrappingInstagram()
        return scrappingInstagram.getData(url)
    }

    /**
     * After completing background task Dismiss the progress dialog
     */
    override fun onPostExecute(data: Map<String, String>) {
        pDialog!!.dismiss()
        onCompleteCallback!!.onComplete(data)
    }

    interface onCompletedCallback {
        fun onComplete(data: Map<String, String>)
    }
}
