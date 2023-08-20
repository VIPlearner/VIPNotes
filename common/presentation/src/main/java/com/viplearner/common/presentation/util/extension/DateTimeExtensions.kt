package com.viplearner.common.presentation.util.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.epochTo12HrFormat(): String{
    if(this == 0L) return ""
    val date =
        Date(this)
    val formatter = SimpleDateFormat(
        "HH:mm a",
        Locale.getDefault()
    )

    return formatter.format(date)
}