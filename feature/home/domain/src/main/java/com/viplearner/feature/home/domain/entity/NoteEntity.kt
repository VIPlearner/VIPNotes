package com.viplearner.feature.home.domain.entity


data class NoteEntity(
    val uuid: String,
    val title: String,
    val content: String,
    val timeLastEdited: Long
)