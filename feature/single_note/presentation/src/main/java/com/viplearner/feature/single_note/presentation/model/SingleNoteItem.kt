package com.viplearner.feature.single_note.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SingleNoteItem(
    val title: String,
    val content: String
) : Parcelable
