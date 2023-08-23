package com.viplearner.feature.single_note.domain.entity

sealed class SingleNoteError {
    data object GetNoteError : SingleNoteError()
    data object CreateNoteError : SingleNoteError()

    data object UpdateNoteError : SingleNoteError()
    data object UnknownError : SingleNoteError()
}
