package com.viplearner.feature.home.data.mapper

import com.viplearner.common.data.local.dto.Note
import com.viplearner.feature.home.data.dto.ListItem
import com.viplearner.feature.home.domain.entity.NoteEntity

fun Note.toNoteEntity(): NoteEntity =
    NoteEntity(
        uuid = uuid,
        title = title,
        content = content,
        timeLastEdited = timeLastEdited
    )