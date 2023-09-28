package com.viplearner.common.data.remote.mapper

import com.viplearner.common.data.remote.dto.Note
import com.viplearner.common.domain.entity.NoteEntity

fun NoteEntity.toNote() =
    Note(
        uuid = uuid,
        title = title,
        content = content,
        timeLastEdited = timeLastEdited,
        isPinned = isPinned,
        isDeleted = isDeleted
    )