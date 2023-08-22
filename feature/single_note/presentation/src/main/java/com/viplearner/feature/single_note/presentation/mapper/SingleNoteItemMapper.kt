package com.viplearner.feature.single_note.presentation.mapper

import com.viplearner.common.domain.entity.NoteEntity
import com.viplearner.feature.single_note.presentation.model.SingleNoteItem

fun NoteEntity.toSingleNoteItem() =
    SingleNoteItem(
        title = title,
        content = content,
    )
