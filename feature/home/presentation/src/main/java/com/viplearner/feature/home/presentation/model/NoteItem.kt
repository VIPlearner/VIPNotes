package com.viplearner.feature.home.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteItem(
    val uuid: String,
    val title : String,
    val content: String,
    val time: String
) : Parcelable
