package com.viplearner.feature.home.presentation.mapper

import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.home.presentation.model.NoteItem

fun NoteEntity.toNoteItem() =
    NoteItem(
        uuid,
        title,
        content,
        timeLastEdited,
        isPinned
    )

//fun NoteItem.toNoteEntity() =
//    NoteEntity(
//        uuid,
//        title,
//        content,
//        timeLastEdited,
//        isPinned
//    )
