package com.viplearner.common.domain.entity

import java.util.UUID

data class NoteEntity(
    val uuid: String = UUID.randomUUID().toString(),
    var title: String,
    var content: String,
    var timeLastEdited: Long,
    val isPinned: Boolean,
    val isDeleted: Boolean = false
)