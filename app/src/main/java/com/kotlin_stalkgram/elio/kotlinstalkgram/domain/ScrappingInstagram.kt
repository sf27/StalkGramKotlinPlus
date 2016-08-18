package com.kotlin_stalkgram.elio.kotlinstalkgram.domain

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class ScrappingInstagram {
    fun getData(url: String): Map<String, String> {
        val head = getHead(url.toString())
        return mapOf(
                VIDEO_KEY to getVideoUrl(head = head).toString(),
                IMAGE_KEY to getImageUrl(head = head).toString(),
                USERNAME_KEY to getUsername(head = head).toString()
        )
    }

    private fun getHead(url: String): Element {
        val head: Element?
        try {
            val doc = Jsoup.connect(url).get()
            head = doc.head()
        } catch (e: Exception) {
            head = null
        }

        return head!!
    }

    private fun getUsername(head: Element): String? {
        val elements: Elements = head.getElementsByAttributeValue("property", "og:description")
        val description: String = elements[0]?.attr("content").toString()
        val indexBefore: Int = description.indexOf("@")
        val indexAfter: Int = description.indexOf(" ", indexBefore)
        return description.substring(indexBefore, indexAfter)
    }

    private fun getImageUrl(head: Element): String? {
        val elements = head.getElementsByAttributeValue("property", "og:image")!!
        return if (elements.size > 0) elements[0].attr("content") else null
    }

    private fun getVideoUrl(head: Element): String? {
        val elements = head.getElementsByAttributeValue("property", "og:video")
        return if (elements.size > 0) elements[0].attr("content") else null
    }

    companion object {
        val VIDEO_KEY = "video"
        val IMAGE_KEY = "image"
        val USERNAME_KEY = "user"
    }
}