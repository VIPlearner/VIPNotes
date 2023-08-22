package com.viplearner.common.presentation.util.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.epochTo12HrFormat(): String{
    val currentTime = System.currentTimeMillis()
    val timeDifference = currentTime - this

    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val date = Date(this)
    val formattedDate = dateFormat.format(date)
    val formattedTime = timeFormat.format(date)

    return when {
        timeDifference <= 86400000L -> formattedTime
        timeDifference <= 172800000L -> "Yesterday $formattedTime"
        else -> "$formattedDate $formattedTime"
    }
}

fun Long.epochToDateFormat(): String {
    val currentTime = System.currentTimeMillis()

    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val date = Date(this)

    return dateFormat.format(date)
}

fun String.parseTimeStringIntoEpochTime(): Long {
    val currentTime = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())

    val yesterdayTime = currentTime - 172800000L
    yesterdayTime.epochToDateFormat()

    val date = when {
        this.startsWith("Today") -> this.replace("Today", "").trim()
        this.startsWith("Yesterday") -> this.replace("Yesterday", yesterdayTime.epochToDateFormat()).trim()
        else -> this
    }

    val parsedDate = dateFormat.parse(date)
    return parsedDate?.time ?: 0L
}