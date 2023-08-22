package com.viplearner.feature.home.domain.entity

import com.viplearner.common.domain.entity.NoteEntity

data class GetNoteEntityListResponse(
    val list: List<NoteEntity>
)
