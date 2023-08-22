package com.viplearner.common.data.local.mapper

import com.viplearner.common.data.local.dto.Note
import com.viplearner.common.domain.entity.NoteEntity

fun NoteEntity.toNote(): Note =
    Note(
        uuid = uuid,
        title = title,
        content = content,
        timeLastEdited = timeLastEdited,
        isPinned = isPinned
    )