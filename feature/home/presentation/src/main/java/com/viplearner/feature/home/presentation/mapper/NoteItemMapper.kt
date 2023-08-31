package com.viplearner.feature.home.presentation.mapper

import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.home.presentation.model.NoteItem

fun NoteEntity.toNoteItem() =
    NoteItem(
        uuid,
        title.replace("\n", "\t"),
        content.replace("\n", "\t"),
        timeLastEdited,
        isPinned,
        false
    )

//fun NoteItem.toNoteEntity() =
//    NoteEntity(
//        uuid,
//        title,
//        content,
//        timeLastEdited,
//        isPinned
//    )
