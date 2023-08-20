package com.viplearner.feature.home.presentation.mapper

import com.viplearner.common.presentation.util.extension.epochTo12HrFormat
import com.viplearner.feature.home.domain.entity.NoteEntity
import com.viplearner.feature.home.presentation.model.NoteItem

fun NoteEntity.toNoteItem() =
    NoteItem(
        uuid,
        title,
        content,
        timeLastEdited.epochTo12HrFormat()
    )

//fun NoteItem.toNoteEntity() =
//    NoteEntity(name, imageURL)
