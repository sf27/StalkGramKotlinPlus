package com.kotlin_stalkgram.elio.kotlinstalkgram.domain

import android.os.Environment

import com.kotlin_stalkgram.elio.kotlinstalkgram.BuildConfig

object AppConstant {

    val ROOT_PATH = BuildConfig.ROOT_PATH

    val IMAGES_PATH = "/" + "images"

    val VIDEOS_PATH = "/" + "videos"

    val DOWNLOAD_DIRECTORY = Environment.getExternalStorageDirectory().absolutePath + "/" + ROOT_PATH

    val DOWNLOAD_DIRECTORY_IMAGES = DOWNLOAD_DIRECTORY + "/" + IMAGES_PATH

    val DOWNLOAD_DIRECTORY_VIDEOS = DOWNLOAD_DIRECTORY + "/" + VIDEOS_PATH

}
