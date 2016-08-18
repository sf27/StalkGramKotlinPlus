package com.kotlin_stalkgram.elio.kotlinstalkgram.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import com.kotlin_stalkgram.elio.kotlinstalkgram.R
import java.io.File

/**
 * Created by elio on 8/5/16.
 */
object FileUtils {

    fun shareFile(filePath: String, context: Context) {
        val shareIntent = Intent(Intent.ACTION_SEND)

        val file = File(filePath)
        val imageUri = Uri.fromFile(file)

        shareIntent.data = imageUri
        val extension = MimeTypeMap.getFileExtensionFromUrl(
                Uri.fromFile(file).toString())
        val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                extension)
        shareIntent.type = mimetype
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        val msn = context.resources.getString(R.string.action_settings)
        context.startActivity(Intent.createChooser(shareIntent, msn))
    }

    fun setImageAs(filePath: String, context: Context) {
        val setAsIntent = Intent(Intent.ACTION_ATTACH_DATA)

        val file = File(filePath)
        val imageUri = Uri.fromFile(file)

        val extension = MimeTypeMap.getFileExtensionFromUrl(
                Uri.fromFile(file).toString())
        val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                extension)

        setAsIntent.setDataAndType(imageUri, mimetype)
        setAsIntent.putExtra("mimeType", mimetype)
        setAsIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        setAsIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val msn = context.resources.getString(R.string.action_settings)
        context.startActivity(Intent.createChooser(setAsIntent, msn))
    }
}
