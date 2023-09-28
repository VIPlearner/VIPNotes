package com.viplearner.common.data.remote.dto

import com.google.firebase.database.PropertyName
import java.util.UUID

data class Note(
    @get:PropertyName("uuid") @set:PropertyName("uuid") var uuid: String = UUID.randomUUID().toString(),
    @get:PropertyName("title") @set:PropertyName("title") var title: String = "",
    @get:PropertyName("content") @set:PropertyName("content") var content: String = "",
    @get:PropertyName("timeLastEdited") @set:PropertyName("timeLastEdited") var timeLastEdited: Long = System.currentTimeMillis(),
    @get:PropertyName("isPinned") @set:PropertyName("isPinned") var isPinned: Boolean = false,
    @get:PropertyName("isDeleted") @set:PropertyName("isDeleted") var isDeleted: Boolean = false,
)