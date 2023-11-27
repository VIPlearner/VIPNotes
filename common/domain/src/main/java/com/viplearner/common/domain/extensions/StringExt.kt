package com.viplearner.common.domain.extensions

fun String?.isValidUrl(): Boolean {
    try {
        if (this == null) return false
        val url = java.net.URL(this)
        url.toURI()
        return true
    } catch (e: Exception) {
        return false
    }
}